package models;

/**
 * This class is for boxing the response sent back to slack by the bot.
 * @author sabinapokhrel
 */

public class ResponseToClient {
  public String status; // Status of the response sent back to slack. It can be either success or fail.
  public String message; // The message sent back to the slack by the bot.\

  public ResponseToClient(String status, String message) {
    this.status = status;
    this.message = message;
  }
}
