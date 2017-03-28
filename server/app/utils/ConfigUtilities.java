package utils;

import models.JiraAuth;
import play.Configuration;
import play.api.Play;

/**
 * @author dxli
 *
 */

public class ConfigUtilities {

    private static Configuration configuration = Play.current().injector().instanceOf(Configuration.class);;

    public static JiraAuth getJiraInfo() {
        return new JiraAuth(configuration.getString("jira.username"), configuration.getString("jira.password"));
    }

    public static String getString(String name){ return configuration.getString(name); }
}
