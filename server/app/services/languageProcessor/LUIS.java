package services.languageProcessor;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.api.Play;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import services.IntentEntity;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

/**
 * Created by sabinapokhrel on 11/21/16.
 */
public class LUIS {

  private Configuration configuration;
  private String query;
  private WSClient ws;


  public LUIS(String query, WSClient ws) {
    configuration = Play.current().injector().instanceOf(Configuration.class);
    this.query = query;
    this.ws = ws;
  }

  public IntentEntity handleQuery() throws ExecutionException, InterruptedException{

    return getLuisResult(query);
  }

  /**
   * This method connects to the LUIS API and sends a query.
   * @param query is the question by a user to POET on slack channel.
   * @return the result of classification performed by LUSI on the asked query as a JSON object.
   */
  private IntentEntity getLuisResult(String query) throws ExecutionException, InterruptedException{

    CompletionStage<JsonNode> responsePromise = getTask(query);

      JsonNode node = responsePromise.toCompletableFuture().get();
      return taskMapping(node);
  }

  /**
   * Connects to LUIS API using HTTPS request.
   * LUIS API processes the query and returns intent and entities as JSON object
   * @param query
   * @return
   */
  private CompletionStage<JsonNode> getTask(String query) {

    String luisurl = configuration.getString("luis.url");
    String appid = configuration.getString("luis.appId");
    String key = configuration.getString("luis.subscription-key");

    WSRequest request = ws.url(luisurl);

    WSRequest complexRequest = request.setQueryParameter("subscription-key",key).setQueryParameter("q",query);

    return complexRequest.get().thenApply(WSResponse:: asJson);
  }


  /**
   * This method gets the topScoring intent and identified entities
   * from LUIS API and calls methods to perform the required action.
   * @param responseBody
   * @return
   */
  private IntentEntity taskMapping(JsonNode responseBody) {
    System.out.print((responseBody.toString()));
    String topScoringIntent = responseBody.get("topScoringIntent").get("intent").toString().replace("\"", "");

    String entity = null;

    if(responseBody.get("entities").findValues("entity").size() != 0) {
      entity = responseBody.get("entities").findValues("entity").get(0).toString().replaceAll("\"", "").replaceAll("\\s", "");
    }
    String entityType = null;
    if(responseBody.get("entities").findValues("type").size() != 0) {
      entityType = responseBody.get("entities").findValues("type").get(0).toString().replace("\"", "").replace(" ", "");
    }

    IntentEntity intentEntity = new IntentEntity();
    intentEntity.intent = topScoringIntent;
    intentEntity.entityType = entityType;
    intentEntity.entityName = entity;

    return intentEntity;
  }
}