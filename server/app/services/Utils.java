package services;

import play.Configuration;

import java.io.*;

public class Utils {

  public static JiraInfo getJiraInfo(Configuration configuration){
   return new JiraInfo(configuration.getString("jira.account"), configuration.getString("jira.pwd"));
  }

  public static void writeMissedQuery(String missedQuestion){
    try {
      BufferedWriter out = new BufferedWriter
        (new FileWriter("logs/missedQuery.log", true));
      out.write(missedQuestion+"\n");
      out.close();
    } catch (FileNotFoundException e) {
      System.out.println(System.getProperty("user.dir"));
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

