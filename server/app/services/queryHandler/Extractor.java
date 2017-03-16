package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;
import services.Utils;


public class Extractor {

  public static JsonNode extractJson(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages");
    } else {

      switch (key) {
          case "description":
              return json.get("fields").get(key);
          case "assignee":
              return json.get("assignee").get("displayname");
          case "status":
              return json.get("status").get("name");
          default:
              return null;
      }
    }
  }

  public static String extractString(JsonNode json, String key) {


    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    } else {

      switch (key) {
          case "description":
              String description = json.get("fields").get(key).textValue();
              return description;
          case "assignee":
              return json.get("fields").get("assignee").get("displayName").textValue();
          case "status":
              return json.get("fields").get("status").get("name").textValue();
        default:
          break;
      }
    }
    return "Errorrrrrrr";
  }
}
