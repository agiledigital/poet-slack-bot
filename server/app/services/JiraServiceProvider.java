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

  private static final String LUIS_ENTITYNAME_STATUS_IN_PROGRESS = "inprogress";
  private static final String LUIS_ENTITYNAME_STATUS_COMPLETED = "completed";
  private static final String LUIS_ENTITYNAME_STATUS_SCOPED = "scoped";
  private static final String LUIS_ENTITYNAME_STATUS_CLOSED = "ReleaseReady";

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
          return ok(jiraReaderService.readTicketsFromJira(response, intent, ticketNo));
        } else {
          return ok(Json.toJson(new ResponseToClient(REQUEST_FAILURE, ConfigUtilities.getString("error-message.invalid-question"))));
        }
      });
  }

  public CompletionStage<Result> readAssingeeInfo(String intent, String jiraUsername) {
    // requests for JIRA page using API
    CompletionStage<JsonNode> responsePromise = jiraReaderService.fetchAssigneeInfoByApi(jiraUsername);

    return responsePromise.thenApply(response -> {
      if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
        // extracts relevant info
        return ok(jiraReaderService.readTicketsFromJira(response, intent, jiraUsername));
      } else {
        // gives an error back if no question found
        return ok(Json.toJson(new ResponseToClient(REQUEST_FAILURE, ConfigUtilities.getString("error-message.invalid-question"))));
        }
    });
  }

  /**
   * Given a status in the workflow, replies with numbers of tickets in this
   * status.
   *
   * @param intent one field defined in LUIS response, describe the type of question.
   * @param status one field defined in LUIS response, originally as entityName.
   *               Here as we are querying tickets by status, thus entityName is
   *               the status in the workflow.
   * @return
   */
  public CompletionStage<Result> readIssuesbyStatus(String intent, String status) {
    final String updatedStatus = mapEntityNameToStatus(status);
    // requests for JIRA page using API
    CompletionStage<JsonNode> responsePromise = jiraReaderService.fetchIssuesForStatusByApi(updatedStatus);

    return responsePromise.thenApply(response -> {
      if (!intent.equals(JiraServiceProvider.NOT_FOUND_ERROR)) {
        // extracts relevant info
        return ok(jiraReaderService.readTicketsFromJira(response, intent, updatedStatus));
      } else {
        // gives an error back if no question found
        return ok(Json.toJson(new ResponseToClient(REQUEST_FAILURE,
          ConfigUtilities.getString("error-message.invalid-question"))
          ));
      }
    });
  }

  /**
   * Map the entity name returned from LUIS to status
   * @param entityName entity name defined in LUIS
   * @return a string showing status on JIRA board.
   */
  private String mapEntityNameToStatus(String entityName){
    String statusOnJiraBoard = null;
    if(entityName.equals(JiraServiceProvider.LUIS_ENTITYNAME_STATUS_IN_PROGRESS))
      statusOnJiraBoard = "in progress";
    else if(entityName.equals(JiraServiceProvider.LUIS_ENTITYNAME_STATUS_COMPLETED))
      statusOnJiraBoard = "closed";
    else if(entityName.equals(JiraServiceProvider.LUIS_ENTITYNAME_STATUS_SCOPED))
      statusOnJiraBoard = "READY";
    else if(entityName.equals(JiraServiceProvider.LUIS_ENTITYNAME_STATUS_CLOSED))
      statusOnJiraBoard = "closed";
    else
      statusOnJiraBoard = entityName;

    return statusOnJiraBoard;
  }
}
