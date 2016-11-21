package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;

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
        System.out.println("Check point 1");
        System.out.println(json.get("fields").get(key).toString());
        return json.get("fields").get(key).textValue();
      } else if (key == "assignee") {
        System.out.println("Check point 2");
        System.out.println(json.get("fields").get("assignee").get("displayName").toString());
        return json.get("fields").get("assignee").get("displayName").textValue();
      }
    }

    return "";
  }

  /**
   * This method extracts the basic details of an Issue
   * fro the passed JSON object.
   * @param json
   * @param key
   * @return
   */
  public static String getIssueBrief(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return json.get("fields").get("assignee").get("displayName").textValue();
    }
  }

  /**
   * This method extracts the description of an Issue
   * fro the passed JSON object.
   * @param json
   * @param key
   * @return
   */
  public static String getIssueDscription(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return json.get("fields").get(key).textValue();
    }
  }

  /**
   * This method extracts the assignee of an Issue
   * fro the passed JSON object.
   * @param json
   * @param key
   * @return
   */
  public static String getIssueAssignee(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return json.get("fields").get("assignee").get("displayName").textValue();
    }
  }

}
