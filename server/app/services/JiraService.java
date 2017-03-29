package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.JiraAuth;
import models.ResponseToClient;
import play.libs.Json;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import utils.ConfigUtilities;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 *  The class is in charge of interactions with JIRA server.
 */
public class JiraService {

    static final String NOT_FOUND_ERROR = "NoQuestionFound";
    static final String REQUEST_SUCCESS = "success";
    static final String REQUEST_FAILURE = "fail";

    private WSClient ws;
    private JiraAuth jiraAuth;
    private JiraReader jiraReader;

    public JiraService(WSClient ws) {
        this.ws = ws;
        this.jiraAuth = ConfigUtilities.getJiraAuth();
        this.jiraReader = new JiraReader();
    }

    public CompletionStage<Result> readTicket(String intent, String ticketNo) {
        // requests for JIRA page using API
        CompletionStage<JsonNode> responsePromise = fetchJiraApi(ticketNo);

        return responsePromise.thenApply(response -> {
            if (!intent.equals(JiraService.NOT_FOUND_ERROR)) {
                // extracts relevant info
                return ok(jiraReader.read(response, intent, ticketNo));
            } else {
                // gives an error back if no question found
                return ok(encodeErrorInJson(ConfigUtilities.getString("error-message.invalid-question")));
            }
        });
    }

    /**
     * To request for JIRA ticket info page via REST API. A non-blocking call.
     *
     * @param ticketId ticket ID in string.
     * @return info page encoded in JSON.
     */
    private CompletionStage<JsonNode> fetchJiraApi(String ticketId) {

        WSRequest request = ws.url(ConfigUtilities.getString("jira.baseUrl")
                + ConfigUtilities.getString("jira.issueEndpoint")
                + ticketId);
        WSRequest complexRequest = request.setAuth(jiraAuth.username, jiraAuth.password, WSAuthScheme.BASIC);

        return complexRequest.get().thenApply(WSResponse::asJson);
    }

    public JsonNode encodeErrorInJson(String message) {
        return Json.toJson(new ResponseToClient("fail", message));
    }
}
