package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Configuration;
import play.api.Play;

import java.io.*;

public class Utils {
  public static JiraInfo getJiraInfo(Configuration configuration){
   return new JiraInfo(configuration.getString("jira.username"), configuration.getString("jira.password"));
  }

  public static void writeMissedQuery(String missedQuestion){
    try {
      BufferedWriter out = new BufferedWriter
        (new FileWriter("logs/missedQuery.log", true));
      out.write(missedQuestion+"\n");
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static JsonNode hyperlinkTicketNo(JsonNode responseBody){
    Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

    String jiraIssueName = configuration.getString("jira.issueName");
    String pattern = "(" + jiraIssueName + "-\\d+)";
    String issueDescription = responseBody.get("fields").get("description").toString();
    String issueUrl = configuration.getString("jira.baseUrl") + "/browse/";
    String hyperlink = "<" + issueUrl + "$1|$1>";

    ObjectNode responseObj;
    ObjectNode objectNode = (ObjectNode) responseBody.get("fields");
    issueDescription = issueDescription.replaceAll(pattern, hyperlink);

    objectNode.put("description", issueDescription);

    responseObj = (ObjectNode) responseBody;
    responseObj.set("fields", objectNode);

    return responseBody;
  }
}

