package services;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import models.LuisResponse;
import utils.ConfigUtilities;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

/**
 * This class provides a set of services from LUIS.
 * @author sabinapokhrel
 */

public class LuisServiceProvider {


  private WSClient ws;

  /**
   * Constructor.
   * @param ws web service instance for requesting LUIS.
   */
  public LuisServiceProvider(WSClient ws) {
    this.ws = ws;
  }

  /**
   * This method first requests for LUIS, then get the intent
   * and entities with top scores. Score is the way LUIS use
   * to measure the confidence on language understanding result.
   *
   * @param query a question from user slack channel
   * @return the result of classification performed by LUIS on the asked query as a JSON object.
   */
  public LuisResponse interpretQuery(String query) throws ExecutionException, InterruptedException {
    // request for natural language understanding results
    CompletionStage<JsonNode> luisResponsePromise = fetchLuisApi(query);
    JsonNode luisResponse = luisResponsePromise.toCompletableFuture().get();

    // classify those results
    return getTopScoringIntentEntity(luisResponse);
  }

  /**
   * Connects to LuisServiceProvider API using HTTPS request.
   * LuisServiceProvider API processes the query.
   *
   * @param query A string showing question asked by user.
   * @return intent and entities as JSON object
   */
  private CompletionStage<JsonNode> fetchLuisApi(String query) {

    String luisurl = ConfigUtilities.getString("luis.url");
    String key = ConfigUtilities.getString("luis.subscription-key");

    WSRequest request = ws.url(luisurl);
    WSRequest complexRequest = request.setQueryParameter("subscription-key", key).setQueryParameter("q", query);

    return complexRequest.get().thenApply(WSResponse::asJson);
  }


  /**
   * This method gets the topScoring intent and identified entities
   * from LuisServiceProvider API.
   *
   * @param responseBody raw response from LUIS
   * @return the intent and entities with top scoring as a LuisResponse instance.
   */
  private LuisResponse getTopScoringIntentEntity(JsonNode responseBody) {
    String entityName;
    String entityType;
    String topScoringIntent = responseBody.get("topScoringIntent").get("intent").toString().replace("\"", "");

    if (responseBody.get("entities").findValues("entity").size() != 0 &&
      responseBody.get("entities").findValues("type").size() != 0) {
      entityName = responseBody.get("entities").findValues("entity").get(0).toString().replaceAll("\"", "").replaceAll("\\s", "");
      entityType = responseBody.get("entities").findValues("type").get(0).toString().replace("\"", "").replace(" ", "");
    } else {
      entityName = null;
      entityType = null;
    }

    return new LuisResponse(topScoringIntent, entityType, entityName);
  }
}