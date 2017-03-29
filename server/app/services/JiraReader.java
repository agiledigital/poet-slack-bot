package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.ResponseToClient;
import play.libs.Json;
import utils.ConfigUtilities;

/**
 *  The class is for all "reading actions" on JIRA.
 *  Extracting fields from raw response on need.
 */
public class JiraReader {
    private String messageToReturn;

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
            return toJson("success", messageToReturn);
        } else {
            return toJson("fail", ConfigUtilities.getString("error-message.issue-not-found"));
        }
    }

    /**
     * This method requests assignee of issue and returns it to the calling method
     *
     * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
     * @param responseBody is the JSON object received from JIRA Rest API.
     * @return true if success, otherwise if no such ticket exists, false.
     */
    public Boolean readAssignee(String issueKey, JsonNode responseBody) {
        if (responseBody.get("errorMessages") != null){
            return false;
        } else {
            this.messageToReturn = responseBody.get("fields").get("assignee").get("displayName").textValue()
                    + " is working on "
                    + issueKey + ".";
            return true;
        }
    }

    /**
     * This method requests brief description of issue and returns it to the calling method
     *
     * @param responseBody is the JSON object received from JIRA Rest API.
     * @return true if success, otherwise if no such ticket exists, false.
     */
    public Boolean readDescription(String ticketNo, JsonNode responseBody) {
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
     * This method requests status of an issue and returns it to the calling method
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
     * This method takes String as an input as returns a JSON object in the required format
     *
     * @param message
     * @return
     */
    private static JsonNode toJson(String ticketNo, String message) {
        ResponseToClient response = new ResponseToClient(ticketNo, message);

        System.out.println("ResponseToClient: " + response.message);
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
        String pattern = "(" + jiraIssueName + "-\\d+)";
        String issueUrl = ConfigUtilities.getString("jira.baseUrl") + "/browse/";
        String hyperlink = "<" + issueUrl + "$1|$1>";

        issueDescription = issueDescription.replaceAll(pattern, hyperlink);

        return issueDescription;
    }

}
