package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;
import services.Utils;

public class Extractor {

  public static JsonNode extractJson(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages");
    } else {

      if (key == "description") {
        return json.get("fields").get(key);
      } else if (key == "assignee") {
        return json.get("assignee").get("name");
      }
    }

    return null;
  }


  public static String extractString(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    } else {

      if (key == "description") {
        String description = json.get("fields").get(key).textValue();
        return Utils.hyperlinkTicketNo(description);
      } else if (key == "assignee") {
        return json.get("fields").get("assignee").get("displayName").textValue();
      }
    }

    return "";
  }
}
