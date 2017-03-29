package controllers;

import models.LuisResponse;
import play.libs.ws.WSClient;
import play.mvc.Result;
import services.JiraService;
import services.LuisService;
import utils.ConfigUtilities;

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
            LuisResponse luisResponse = luisService.fetchIntentEntity();

            // actions on reading Jira go here
            if (luisResponse.intent.equals("IssueBrief") ||
                    luisResponse.intent.equals("IssueAssignee") ||
                    luisResponse.intent.equals("IssueStatus")) {
                return jiraService.readTicket(luisResponse.intent, luisResponse.entityName);
            } else {
                // TODO: actions to update JIRA go here. e.g. changing workflow, etc.
                // return jiraService.updateTicket(luisResponse.intent, luisResponse.entityName);
                return CompletableFuture.supplyAsync(() -> ok(jiraService.encodeErrorInJson
                        (ConfigUtilities.getString("error-message.action-not-supported"))));
            }
        } catch (Exception e) {
            // TODO: questions not understood goes here
            return CompletableFuture.supplyAsync(() -> ok(jiraService.encodeErrorInJson(
                    ConfigUtilities.getString("error-message.luis-error"))));
        }
    }
}
