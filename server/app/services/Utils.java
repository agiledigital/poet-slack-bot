package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Dongxu on 9/29/2016.
 */
public class Utils {

  public static JiraInfo getJiraInfo(){
    JiraInfo jiraInfo = new JiraInfo();

    File configFile = new File("conf/config.properties");

    try {
      FileReader reader = new FileReader(configFile);
      Properties props = new Properties();
      props.load(reader);

      jiraInfo.account = props.getProperty("account");
      jiraInfo.pwd = props.getProperty("pwd");

      reader.close();
    } catch (FileNotFoundException ex) {
      // file does not exist
      System.out.println(ex.getMessage());
      System.out.println(System.getProperty("user.dir"));
    } catch (IOException ex) {
      // I/O error
    }
    return jiraInfo;
  }
}

