package controllers;

import models.LuisResponse;
import play.libs.ws.WSClient;
import play.mvc.Result;
import services.JiraService;
import services.language.processing.LuisService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

public class UserQueryHandler {

    private LuisService luisService;
    private JiraService jiraService;

    public UserQueryHandler(String query, WSClient ws) {
        this.luisService = new LuisService(query, ws);
        this.jiraService = new JiraService(ws);
    }

    public CompletionStage<Result> handleUserQuery() {
        try {
            LuisResponse luisResponse = luisService.extractIntentEntity();
            return jiraService.fetchJiraTicketInfo(luisResponse.intent, luisResponse.entityName);
        } catch (Exception e) {
            return CompletableFuture.supplyAsync(() -> ok(jiraService.encodeErrorInJson("Error with LuisService. Either LuisService is not configured properly" +
                    " or environment variable for LuisService is not set.")));
        }
    }
}
