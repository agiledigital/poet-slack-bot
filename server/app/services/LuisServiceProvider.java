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
 * @author sabinapokhrel
 */

public class LuisServiceProvider {

    private String query;
    private WSClient ws;

    public LuisServiceProvider(String query, WSClient ws) {
        this.query = query;
        this.ws = ws;
    }

    /**
     * This method connects to the LuisServiceProvider API and sends a query.
     *
     * @return the result of classification performed by LUIS on the asked query as a JSON object.
     */
    public LuisResponse interpretQuery() throws ExecutionException, InterruptedException {
        // request for natural language understanding results
        CompletionStage<JsonNode> luisResponsePromise = fetchLuisApi(query);
        JsonNode luisResponse = luisResponsePromise.toCompletableFuture().get();

        // classify those results
        return classifyResponse(luisResponse);
    }

    /**
     * Connects to LuisServiceProvider API using HTTPS request.
     * LuisServiceProvider API processes the query and returns intent and entities as JSON object
     *
     * @param query
     * @return
     */
    private CompletionStage<JsonNode> fetchLuisApi(String query) {

        String luisurl = ConfigUtilities.getString("luis.url");
        // String appid = ConfigUtilities.getString("luis.appId");
        String key = ConfigUtilities.getString("luis.subscription-key");

        WSRequest request = ws.url(luisurl);
        WSRequest complexRequest = request.setQueryParameter("subscription-key", key).setQueryParameter("q", query);

        return complexRequest.get().thenApply(WSResponse::asJson);
    }


    /**
     * This method gets the topScoring intent and identified entities
     * from LuisServiceProvider API and calls methods to perform the required action.
     *
     * @param responseBody
     * @return
     */
    private LuisResponse classifyResponse(JsonNode responseBody) {
        String entityName;
        String entityType;
        String topScoringIntent = responseBody.get("topScoringIntent").get("intent").toString().replace("\"", "");

        if (responseBody.get("entities").findValues("entity").size() != 0 &&
                responseBody.get("entities").findValues("type").size() != 0) {
            entityName = responseBody.get("entities").findValues("entity").get(0).toString().replaceAll("\"", "").replaceAll("\\s", "");
            entityType = responseBody.get("entities").findValues("type").get(0).toString().replace("\"", "").replace(" ", "");
        } else {
            throw new NullPointerException();
        }
        return new LuisResponse(topScoringIntent, entityType, entityName);
    }
}