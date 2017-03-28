package services.language.processing;

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
 * @author sabinapokhrel
 *
 */

public class LuisService {

    private String query;
    private WSClient ws;


    public LuisService(String query, WSClient ws) {
        this.query = query;
        this.ws = ws;
    }

    /**
     * This method connects to the LuisService API and sends a query.
     *
     * @return the result of classification performed by LUSI on the asked query as a JSON object.
     */
    public LuisResponse extractIntentEntity() throws ExecutionException, InterruptedException {

        CompletionStage<JsonNode> luisResponsePromise = fetchLuisApi(query);

        JsonNode luisResponse = luisResponsePromise.toCompletableFuture().get();
        return mapResponseToIntentEntity(luisResponse);
    }

    /**
     * Connects to LuisService API using HTTPS request.
     * LuisService API processes the query and returns intent and entities as JSON object
     *
     * @param query
     * @return
     */
    private CompletionStage<JsonNode> fetchLuisApi(String query) {

        String luisurl = ConfigUtilities.getString("luis.url");
        String appid = ConfigUtilities.getString("luis.appId");
        String key = ConfigUtilities.getString("luis.subscription-key");

        WSRequest request = ws.url(luisurl);
        WSRequest complexRequest = request.setQueryParameter("subscription-key", key).setQueryParameter("q", query);

        return complexRequest.get().thenApply(WSResponse::asJson);
    }


    /**
     * This method gets the topScoring intent and identified entities
     * from LuisService API and calls methods to perform the required action.
     *
     * @param responseBody
     * @return
     */
    private LuisResponse mapResponseToIntentEntity(JsonNode responseBody) {
        System.out.print(responseBody.toString());
        String topScoringIntent = responseBody.get("topScoringIntent").get("intent").toString().replace("\"", "");

        String entityName;
        String entityType;

        if (responseBody.get("entities").findValues("entity").size() != 0) {
            entityName = responseBody.get("entities").findValues("entity").get(0).toString().replaceAll("\"", "").replaceAll("\\s", "");
        } else {
            // TODO what to do if the condition is false?
            entityName = null;
        }
        if (responseBody.get("entities").findValues("type").size() != 0) {
            entityType = responseBody.get("entities").findValues("type").get(0).toString().replace("\"", "").replace(" ", "");
        } else {
            // TODO what to do if the condition is false?
            entityType = null;
        }

        return new LuisResponse(topScoringIntent, entityType, entityName);
    }
}