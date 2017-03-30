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
 *  The class is for all "reading actions" on JIRA.
 *  Extracting fields from raw response on need.
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

    public JsonNode read(JsonNode response, String intent, String ticketNo) {
        Boolean isSuccess = false;

        switch (intent) {
            case "IssueBrief" :
                isSuccess = readDescription(ticketNo, response);
                break;
            case "IssueAssignee" :
                isSuccess = readAssignee(ticketNo, response);
                break;
            case "IssueStatus" :
                isSuccess = readStatus(ticketNo, response);
                break;
        }

        if (isSuccess) {
            return toJson(JiraServiceProvider.REQUEST_SUCCESS, messageToReturn);
        } else {
            return toJson(JiraServiceProvider.REQUEST_FAILURE, ConfigUtilities.getString("error-message.issue-not-found"));
        }
    }

    /**
     * This method reads assignee of the ticket.
     *
     * @param ticketNo     is the IssueID of type string which was mentioned in the query by the user.
     * @param responseBody is the JSON object received from JIRA Rest API.
     * @return true if success, otherwise if no such ticket exists, false.
     */
    public Boolean readAssignee(String ticketNo, JsonNode responseBody) {
        if (responseBody.get("errorMessages") != null){
            return false;
        } else {
            this.messageToReturn = responseBody.get("fields").get("assignee").get("displayName").textValue()
                    + " is working on "
                    + ticketNo + ".";
            return true;
        }
    }

    /**
     * This method reads description of the ticket.
     *
     * @param responseBody is the JSON object received from JIRA Rest API.
     * @return true if success, otherwise if no such ticket exists, false.
     */
    public Boolean readDescription(String ticketNo, JsonNode responseBody) {
        if (responseBody.get("errorMessages") != null) {
            return false;
        } else {
            this.messageToReturn = hyperlinkTicketNo("Description of "
                  //  + '<' + "http://google.com" +'|' +ticketNo+  '>' // replace google by the actual ticket page
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
    public Boolean readStatus(String ticketNo, JsonNode responseBody) {
        if (responseBody.get("errorMessages") != null) {
            return false;
        } else {
            this.messageToReturn ="The status of "
                    + ticketNo
                    + " is "
                    + responseBody.get("fields").get("status").get("name").textValue();
            return true;
        }
    }

    /**
     * This method wraps a response message and status in JSON.
     *
     * @param message
     * @return
     */
    private static JsonNode toJson(String ticketNo, String message) {
        ResponseToClient response = new ResponseToClient(ticketNo, message);
        return Json.toJson(response);
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
    public static String hyperlinkTicketNo(String issueDescription) {
        String jiraIssueName = ConfigUtilities.getString("jira.issueName");
        String pattern = "((?i)" + jiraIssueName + "-\\d+)";
        String issueUrl = ConfigUtilities.getString("jira.baseUrl") + "/browse/";
        String hyperlink = "<" + issueUrl + "$1|$1>";

        issueDescription = issueDescription.replaceAll(pattern, hyperlink);

        System.out.println(issueDescription);
        return issueDescription;
    }

}
