package utils;

import models.JiraAuth;
import play.Configuration;
import play.api.Play;

/**
 * This class is a utility class holding a static configuration
 * instance for project getting configurations from
 * conf/application.conf.
 */

public class ConfigUtilities {

  private static Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

  public static JiraAuth getJiraAuth() {
    return new JiraAuth(configuration.getString("jira.username"), configuration.getString("jira.password"));
  }

  public static String getString(String name) {
    return configuration.getString(name);
  }
}
