package services.models;

public class JiraUserCredentials {
  public String username;
  public String password;

  public JiraUserCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
