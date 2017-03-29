package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.LuisResponse;
import models.ResponseToClient;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.mvc.Result;
import utils.ConfigUtilities;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 *  The class is in charge of interactions with JIRA server.
 */
public class JiraServiceProvider {

    static final String NOT_FOUND_ERROR = "NoQuestionFound";
    static final String REQUEST_SUCCESS = "success";
    static final String REQUEST_FAILURE = "fail";

    private JiraReaderService jiraReaderService;

    public JiraServiceProvider(WSClient ws) {
        this.jiraReaderService = new JiraReaderService(ws, ConfigUtilities.getJiraAuth());
    }

    public CompletionStage<Result> readOrUpdateJira(LuisResponse luisResponse) {
        // actions on reading Jira go here
        if (luisResponse.intent.equals("IssueBrief") ||
                luisResponse.intent.equals("IssueAssignee") ||
                luisResponse.intent.equals("IssueStatus")) {
            return readTicket(luisResponse.intent, luisResponse.entityName);
        } else {
            // TODO: actions to update JIRA go here. e.g. changing workflow, etc.
            // return jiraServiceManager.updateTicket(luisResponse.intent, luisResponse.entityName);
            return CompletableFuture.supplyAsync(() ->
                    ok(encodeErrorInJson(ConfigUtilities.getString("error-message.action-not-supported"))));
        }
    }

    public CompletionStage<Result> readTicket(String intent, String ticketNo) {
        // requests for JIRA page using API
        CompletionStage<JsonNode> responsePromise = jiraReaderService.fetchJiraApi(ticketNo);

        return responsePromise.thenApply(response -> {
            if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
                // extracts relevant info
                return ok(jiraReaderService.read(response, intent, ticketNo));
            } else {
                // gives an error back if no question found
                return ok(encodeErrorInJson(ConfigUtilities.getString("error-message.invalid-question")));
            }
        });
    }

    public JsonNode encodeErrorInJson(String message) {
        return Json.toJson(new ResponseToClient(REQUEST_FAILURE, message));
    }
}
