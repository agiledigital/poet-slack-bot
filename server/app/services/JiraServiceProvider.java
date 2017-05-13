package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.ResponseToClient;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.mvc.Result;
import utils.ConfigUtilities;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

public class JiraServiceProvider {

  static final String NOT_FOUND_ERROR = "NoQuestionFound";
  static final String REQUEST_SUCCESS = "success";
  static final String REQUEST_FAILURE = "fail";

  private JiraReaderService jiraReaderService;

  public JiraServiceProvider(WSClient ws) {
    this.jiraReaderService = new JiraReaderService(ws, ConfigUtilities.getJiraAuth());
  }

  /**
   * This method is related to read certain field from JIRA ticket queried.
   * Now supports status, description and assignee.
   *
   * @param intent one field defined in LUIS response, describe the type of question.
   * @param ticketNo one field defined in LUIS response, originally as entityName.
   *                 Here as we are querying ticket, thus entityName is just ticket
   *                 number.
   * @return JsonNode contains required info if success, otherwise an error message.
   *
   */
  public CompletionStage<Result> readTicket(String intent, String ticketNo) {
    // requests for JIRA page using API
    return jiraReaderService.fetchTicketByApi(ticketNo).thenApply(response -> {
        if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
          // extracts relevant info
          return ok(jiraReaderService.readTicketInfo(response, intent, ticketNo));
        } else {
          return ok(Json.toJson(new ResponseToClient(REQUEST_FAILURE, ConfigUtilities.getString("error-message.invalid-question"))));
        }
      });
  }

  /**
   * This method is related to read certain field from JIRA ticket queried.
   * Now supports status, description and assignee.
   *
   * @param intent one field defined in LUIS response, describe the type of question.
   * @param jiraUsername one field defined in LUIS response, originally as entityName.
   *                     Here we are querying tickets by assignee JIRA user name, thus
   *                     the entity name is JIRA user name.
   * @return JsonNode contains required info if success, otherwise an error message.
   *
   */
  public CompletionStage<Result> readAssingeeInfo(String intent, String jiraUsername) {
    // requests for JIRA page using API
    CompletionStage<JsonNode> responsePromise = jiraReaderService.fetchAssigneeInfoByApi(jiraUsername);

    return responsePromise.thenApply(response -> {
      if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
        // extracts relevant info
        return ok(jiraReaderService.readTicketInfo(response, intent, jiraUsername));
      } else {
        // gives an error back if no question found
        return ok(Json.toJson(new ResponseToClient(REQUEST_FAILURE, ConfigUtilities.getString("error-message.invalid-question"))));
        }
    });
  }
}
