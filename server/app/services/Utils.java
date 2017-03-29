package services;

import play.Configuration;
import play.api.Play;
import services.models.JiraUserCredentials;

public class Utils {
  public static JiraUserCredentials getJiraInfo(Configuration configuration){
   return new JiraUserCredentials(configuration.getString("jira.username"), configuration.getString("jira.password"));
  }

  public static String hyperlinkTicketNo(String issueDescription){
    /**
     * The methods hyperlinks the ticket number appearing in the
     * description, or actually, any string fed into. This is implemented
     * by first checking the project name configured in the application.conf
     * and then use the slack formatting to wrap around the ticket number.
     *
     * @Reference: https://api.slack.com/docs/message-formatting
     *
     * @param issueDescription the description of the ticket queried.
     *                         This can also be applied to any other strings.
     * @return                 the issue description after formatting. This
     *                         should hyperlink the ticket numbers appearing in
     *                         the input string.
     */
    Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

    String jiraIssueName = configuration.getString("jira.issueName");
    String pattern = "(" + jiraIssueName + "-\\d+)";
    String issueUrl = configuration.getString("jira.baseUrl") + "/browse/";
    String hyperlink = "<" + issueUrl + "$1|$1>";

    issueDescription = issueDescription.replaceAll(pattern, hyperlink);

    return issueDescription;
  }
}

