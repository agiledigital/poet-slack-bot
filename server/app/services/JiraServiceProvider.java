package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.ResponseToClient;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.mvc.Result;
import utils.ConfigUtilities;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 * The class manages instance of jiraReaderService
 * and jiraWriterService (not there yet).
 * <p>
 * First, readOrUpdateJIra() decides readTicketInfo or update
 * operations should be done. Then call Writer/Reader
 * to update/readTicketInfo JIRA.
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
    return jiraReaderService.fetchTicketByApi(ticketNo).thenApply(response -> {
      if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
        // extracts relevant info
        return ok(jiraReaderService.readTicketInfo(response, intent, ticketNo));
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