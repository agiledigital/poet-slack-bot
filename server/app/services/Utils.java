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

  public static String hyperlinkTicketNo(String issueDescription){
    Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

    String jiraIssueName = configuration.getString("jira.issueName");
    String pattern = "(" + jiraIssueName + "-\\d+)";
    String issueUrl = configuration.getString("jira.baseUrl") + "/browse/";
    String hyperlink = "<" + issueUrl + "$1|$1>";

    issueDescription = issueDescription.replaceAll(pattern, hyperlink);

    return issueDescription;
  }
}

