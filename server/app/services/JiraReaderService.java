package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.JiraAuth;
import models.ResponseToClient;
import play.libs.Json;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import utils.ConfigUtilities;

import java.util.concurrent.CompletionStage;

/**
 * The class is for all "reading actions" on JIRA.
 * Extracting fields from raw response on need.
 */
public class JiraReaderService {
  private String messageToReturn;
  private WSClient ws;
  private JiraAuth jiraAuth;


  public JiraReaderService(WSClient ws, JiraAuth jiraAuth) {
    this.ws = ws;
    this.jiraAuth = jiraAuth;
  }

  /**
   * To request for JIRA ticket info page via REST API. A non-blocking call.
   *
   * @param ticketId ticket ID in string.
   * @return info page encoded in JSON.
   */
  public CompletionStage<JsonNode> fetchTicketByApi(String ticketId) {
    WSRequest request = ws.url(ConfigUtilities.getString("jira.baseUrl")
      + ConfigUtilities.getString("jira.issueEndpoint")
      + ticketId);
    WSRequest complexRequest = request.setAuth(jiraAuth.username, jiraAuth.password, WSAuthScheme.BASIC);

    return complexRequest.get().thenApply(WSResponse::asJson);
  }

  /**
   * A facade to determine what ticket info to readTicketInfo. Then call
   * related methods below.
   *
   * @param response response body from HTTP request via JIRA REST API.
   * @param intent one field defined in LUIS response, describe the type of question.
   * @param entity one field defined in LUIS response, originally named as entityName.
   *
   * @return a JsonNode contains the info users requiring. This will eventually be
   * returned to client side and posted to slack channel.
   */
  public JsonNode readTicketInfo(JsonNode response, String intent, String entity) {
    Boolean isSuccess = false;

    switch (intent) {
      case "IssueDescription":
        isSuccess = readDescription(entity, response);
        break;
      case "IssueAssignee":
        isSuccess = readAssignee(entity, response);
        break;
      case "IssueStatus":
        isSuccess = readStatus(entity, response);
        break;
      case "AssigneeIssues" :
        isSuccess = readIssues(entity, response);
        break;
      default:
        isSuccess = false;
    }

    if (isSuccess) {
      return Json.toJson(new ResponseToClient(JiraServiceProvider.REQUEST_SUCCESS, messageToReturn));
    } else {
      return Json.toJson(new ResponseToClient(JiraServiceProvider.REQUEST_FAILURE,
        ConfigUtilities.getString("error-message.issue-not-found")));
    }
  }

   /** Fetches Assignee Info from JIRA API.
   *
   * @param jiraUsername jira username in string.
   * @return info page encoded in JSON.
   */
  public CompletionStage<JsonNode> fetchAssigneeInfoByApi(String jiraUsername) {

    WSRequest request = ws.url("https://jira.agiledigital.com.au/rest/api/2/search")
      .setQueryParameter("jql", "assignee=" + jiraUsername);
    WSRequest complexRequest = request.setAuth(jiraAuth.username, jiraAuth.password, WSAuthScheme.BASIC);
    return complexRequest.get().thenApply(WSResponse::asJson);
  }

  /**
   * This method reads assignee of the ticket.
   *
   * @param ticketNo     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return true if success, otherwise if no such ticket exists, false.
   */
  private Boolean readAssignee(String ticketNo, JsonNode responseBody) {
    if (responseBody.get("errorMessages") != null) {
      return false;
    } else {
      this.messageToReturn = hyperlinkTicketNo(responseBody.get("fields").get("assignee").get("displayName").textValue()
        + " is working on "
        + ticketNo + ".");
      return true;
    }
  }

  /**
   * This method reads description of the ticket.
   *
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return true if success, otherwise if no such ticket exists, false.
   */
  private Boolean readDescription(String ticketNo, JsonNode responseBody) {
    if (responseBody.get("errorMessages") != null) {
      return false;
    } else {
      this.messageToReturn = hyperlinkTicketNo("Description of "
        + ticketNo
        + " is as follows: \n"
        + responseBody.get("fields").get("description").textValue());
      return true;
    }
  }

  /**
   * This method reads status of the ticket.
   *
   * @param ticketNo     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return true if success, otherwise if no such ticket exists, false.
   */
  private Boolean readStatus(String ticketNo, JsonNode responseBody) {
    if (responseBody.get("errorMessages") != null) {
      return false;
    } else {
      this.messageToReturn = hyperlinkTicketNo("The status of "
        + ticketNo
        + " is "
        + responseBody.get("fields").get("status").get("name").textValue());
      return true;
    }
  }

  /**
   * This method give a list of tickets one person is working on.
   *
   * @param assignee assignee's account of JIRA board, rather display name.
   *                 This may needs to be improved in later version.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return true if success, otherwise if no such assignee name exists, false.
   */
  private Boolean readIssues(String assignee, JsonNode responseBody) {
    if (responseBody.get("errorMessages") != null) {
      return false;
    } else {
      int issueCount = Integer.parseInt(responseBody.get("total").toString());
      StringBuffer issues = new StringBuffer();
      for (int i=0, j=0; i<issueCount; i++){
        String string = responseBody.get("issues").findValues("key").get(j).textValue();
        StringBuffer tmp;
        if(i<issueCount-1)
          tmp = new StringBuffer(string + ", ");
        else
          tmp = new StringBuffer(string + ".");
        issues.append(tmp);
        j=j+6;
      }
      this.messageToReturn = hyperlinkTicketNo(assignee + " is working on " + issues.toString());
      return true;
    }
  }

  /**
   * The methods hyperlinks the ticket number appearing in the
   * description, or actually, any string fed into. This is implemented
   * by first checking the project name configured in the application.conf
   * and then use the slack formatting to wrap around the ticket number.
   *
   * @param issueDescription the description of the ticket queried.
   *                         This can also be applied to any other strings.
   * @return the issue description after formatting. This
   * should hyperlink the ticket numbers appearing in
   * the input string.
   * @Reference: https://api.slack.com/docs/message-formatting
   */
  private static String hyperlinkTicketNo(String issueDescription) {
    String jiraIssueName = ConfigUtilities.getString("jira.issueName");
    String pattern = "((?i)" + jiraIssueName + "-\\d+)";
    String issueUrl = ConfigUtilities.getString("jira.baseUrl") + "/browse/";
    String hyperlink = "<" + issueUrl + "$1|$1>";

    issueDescription = issueDescription.replaceAll(pattern, hyperlink);
    return issueDescription;
  }

}
