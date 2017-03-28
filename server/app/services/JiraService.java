package services;

import services.language.processing.TaskMap;
import utils.ConfigUtilities;
import com.fasterxml.jackson.databind.JsonNode;
import models.JiraAuth;
import models.ResponseToClient;
import play.libs.Json;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 * Created by dxli on 28/03/17.
 */
public class JiraService {

    private WSClient ws;
    private JiraAuth jiraAuth;

    public JiraService(WSClient ws) {
        this.ws = ws;
        this.jiraAuth = ConfigUtilities.getJiraInfo();
    }

    public CompletionStage<Result> fetchJiraTicketInfo(String questionMapping, String ticketNo) {

        CompletionStage<JsonNode> responsePromise = fetchJiraApi(ticketNo);

        if (ticketNo.equals("NoIdFound")) {
            return responsePromise.thenApply(response -> ok(encodeErrorInJson("No id Found")));
        }

        return responsePromise.thenApply(response -> {
            if (questionMapping.equals("NoQuestionFound")) {
                return ok(processJiraResponse(response, questionMapping, ticketNo));
            } else {
                return ok(encodeErrorInJson(ConfigUtilities.getString("error-message.invalid-question")));
            }
        });
    }

    public CompletionStage<JsonNode> fetchJiraApi(String ticketId) {

        WSRequest request = ws.url(ConfigUtilities.getString("jira.baseUrl")
                + ConfigUtilities.getString("jira.issueEndpoint")
                + ticketId);
        WSRequest complexRequest = request.setAuth(jiraAuth.username, jiraAuth.password, WSAuthScheme.BASIC);

        return complexRequest.get().thenApply(WSResponse::asJson);
    }

    private JsonNode processJiraResponse(JsonNode responseBody, String questionMapping, String ticketId) {

        JsonNode jsonNode = null;

        try {
            jsonNode = TaskMap.questionMapping(questionMapping, ticketId, responseBody);
        } catch (Exception e) {
            e.getMessage();
        }

        return jsonNode;
    }

    public JsonNode encodeErrorInJson(String message) {
        return Json.toJson(new ResponseToClient("fail", message));
    }
}
