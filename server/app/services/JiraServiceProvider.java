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
 *  The class manages instance of jiraReaderService
 *  and jiraWriterService (not there yet).
 *
 *  First, readOrUpdateJIra() decides read or update
 *  operations should be done. Then call Writer/Reader
 *  to update/read JIRA.
 */
public class JiraServiceProvider {

  static final String NOT_FOUND_ERROR = "NoQuestionFound";
  static final String REQUEST_SUCCESS = "success";
  static final String REQUEST_FAILURE = "fail";

  private JiraReaderService jiraReaderService;

  public JiraServiceProvider(WSClient ws) {
    this.jiraReaderService = new JiraReaderService(ws, ConfigUtilities.getJiraAuth());
  }

  public CompletionStage<Result> readTicket(String intent, String ticketNo) {
    // requests for JIRA page using API
    CompletionStage<JsonNode> responsePromise = jiraReaderService.fetchTicketByApi(ticketNo);

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

  public CompletionStage<Result> readAssingeeInfo(String intent, String jiraUsername) {
    // requests for JIRA page using API
    CompletionStage<JsonNode> responsePromise = jiraReaderService.fetchAssigneeInfoByApi(jiraUsername);

    return responsePromise.thenApply(response -> {
      if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
        // extracts relevant info
        return ok(jiraReaderService.read(response, intent, jiraUsername));
      } else {
        // gives an error back if no question found
        return ok(encodeErrorInJson(ConfigUtilities.getString("error-message.invalid-question")));
      }
    });
  }

  public CompletionStage<Result> readIssuesbyStatus(String intent, String status) {
    final String updatedStatus = updatedStatus(status);
    // requests for JIRA page using API
    CompletionStage<JsonNode> responsePromise = jiraReaderService.fetchIssuesForStatusByApi(updatedStatus);

    return responsePromise.thenApply(response -> {
      if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
        // extracts relevant info
        return ok(jiraReaderService.read(response, intent, updatedStatus));
      } else {
        // gives an error back if no question found
        return ok(encodeErrorInJson(ConfigUtilities.getString("error-message.invalid-question")));
      }
    });
  }

  public JsonNode encodeErrorInJson(String message) {
    return Json.toJson(new ResponseToClient(REQUEST_FAILURE, message));
  }

  public String updatedStatus(String status){
    String updatedStatus = null;
    if(status.equals("inprogress"))
      updatedStatus = "in progress";
    else if(status.equals("completed"))
      updatedStatus = "closed";
    else if(status.equals("scoped"))
      updatedStatus = "READY";
    else if(status.equals("ReleaseReady"))
      updatedStatus = "closed";
    else
      updatedStatus = status;

    return updatedStatus;
  }
}
