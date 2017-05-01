package models;

/**
 * @author sabinapokhrel
 */

public class LuisResponse {
  public String intent;
  public String entityType;
  public String entityName;

  public LuisResponse(String intent, String entityType, String entityName) {
    this.intent = intent;
    this.entityType = entityType;
    this.entityName = entityName;
  }
}
