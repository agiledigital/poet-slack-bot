package models;

/**
 * @author sabinapokhrel
 */

public class ResponseToClient {
  public String status;
  public String message;

  public ResponseToClient(String status, String message) {
    this.status = status;
    this.message = message;
  }
}
