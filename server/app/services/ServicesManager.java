package services;

import models.LuisResponse;
import play.libs.ws.WSClient;
import play.mvc.Result;
import utils.ConfigUtilities;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

public class ServicesManager {

    private LuisServiceProvider luisServiceProvider;
    private JiraServiceProvider jiraServiceProvider;

    public ServicesManager(String query, WSClient ws) {
        this.luisServiceProvider = new LuisServiceProvider(query, ws);
        this.jiraServiceProvider = new JiraServiceProvider(ws);
    }

    public CompletionStage<Result> interpretQueryAndActOnJira() {
        try {
            LuisResponse luisResponse = luisServiceProvider.interpretQuery();
            return jiraServiceProvider.readOrUpdateJira(luisResponse);
        } catch (Exception e) {
            // TODO: questions not understood goes here
            return CompletableFuture.supplyAsync(() -> ok(jiraServiceProvider.encodeErrorInJson(
                    ConfigUtilities.getString("error-message.luis-error"))));
        }
    }
}
