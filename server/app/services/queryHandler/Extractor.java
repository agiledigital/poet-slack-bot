package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;

public class Extractor {

  /**
   * This method extracts the BASIC DETAILS of an Issue
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
   * This method extracts the DESCRIPTION of an Issue
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
   * This method extracts the ASSIGNEE of an Issue
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

  /** COMPLETE THIS METHOD
   * This method extracts the STATUS of an Issue
   * fro the passed JSON object.
   * @param json
   * @param key
   * @return
   */
  public static String getIssueStatus(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return null; /* Fill this part*/
    }
  }

  /** COMPLETE THIS METHOD
   *
   * This method extracts IN-PROGRESS ISSUES
   * fro the passed JSON object.
   * @param json
   * @return
   */
  public static String getInProgressIssues(JsonNode json) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return null; /* Fill this part*/
    }
  }

  /** COMPLETE THIS METHOD
   *
   * This method extracts COMPLETED ISSUES
   * fro the passed JSON object.
   * @param json
   * @return
   */
  public static String getCompletedIssues(JsonNode json) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return null; /* Fill this part*/
    }
  }

  /** COMPLETE THIS METHOD
   *
   * This method extracts STALLED ISSUES
   * fro the passed JSON object.
   * @param json
   * @return
   */
  public static String getStalledIssues(JsonNode json) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return null; /* Fill this part*/
    }
  }

}