package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;
import services.Utils;

public class Extractor {

  /**
   * This method extracts the BASIC DETAILS of an Issue
   * from the passed JSON object.
   * @param json is the JSON object received from JIRA Rest API.
   * @param key is the IssueID of type string the issue in JIRA.
   * @return the Issue Brief of the passed key from the passed json object as a string.
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
   * from the passed JSON object.
   * @param json is the JSON object received from JIRA Rest API.
   * @param key is the IssueID of the issue in JIRA.
   * @return the Issue Description  of the passed key from the passed json object as a string.
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
   * from the passed JSON object.
   * @param json is the JSON object received from JIRA Rest API.
   * @param key is the IssueID of type string the issue in JIRA.
   * @return the Issue Assignee of the passed key from the passed json object as a string.
   */
  public static String getIssueAssignee(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return json.get("fields").get("assignee").get("displayName").textValue();
    }
  }

  /**
   * This method extracts the STATUS of an Issue
   * from the passed JSON object.
   * @param json is the JSON object received from JIRA Rest API.
   * @param key is the IssueID of type string the issue in JIRA.
   * @return the Issue status of the passed key from the passed json object as a string.
   */
  public static String getIssueStatus(JsonNode json, String key) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return json.get("fields").get("status").get("name").textValue();
    }
  }

  /**
   * This method extracts IN-PROGRESS ISSUES
   * from the passed JSON object.
   * @param json is the JSON object received from JIRA Rest API.
   * @return the Issues that Progress from the passed json object as a string.
   */
  public static String getInProgressIssues(JsonNode json) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return null;
    }
  }

  /**
   * This method extracts COMPLETED ISSUES
   * from the passed JSON object.
   * @param json is the JSON object received from JIRA Rest API.
   * @return the Issues completed from the passed json object as a string.
   */
  public static String getCompletedIssues(JsonNode json) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();

    }
    else {
      return null;
    }
  }

  /**
   * This method extracts STALLED ISSUES
   * from the passed JSON object.
   * @param json is the JSON object received from JIRA Rest API.
   * @return the Issues stalled from the passed json object as a string.
   */
  public static String getStalledIssues(JsonNode json) {
    if (json.get("errorMessages") != null) {
      return json.get("errorMessages").toString();
    }
    else {
      return null;
    }
  }

}