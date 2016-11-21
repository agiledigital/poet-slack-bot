package services.languageProcessor;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import scala.util.parsing.json.JSONArray;
import services.IntentEntity;
import services.Response;
import services.queryHandler.Extractor;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class TaskMap {

  /**
   * This method gets the topScoring intent and identified entities
   * from LUIS API and calls methods to perform the required action.
   * @param responseBody
   * @return
   */
  public static JsonNode questionMapping(JsonNode responseBody) {
    System.out.print((responseBody.toString()));
    String topScoringIntent = responseBody.get("topScoringIntent").get("intent").toString();
    List entities = responseBody.get("entities").findValues("entity");
    List entityTypes = responseBody.get("entities").findValues("type");

    IntentEntity intentEntity = new IntentEntity();
    intentEntity.intent = topScoringIntent.toString().replace("\"", "");
    intentEntity.entityType = entityTypes.get(0).toString().replace("\"", "");
    intentEntity.entityName = entities.get(0).toString().replace("\"", "").replace(" ","");

    return Json.toJson((intentEntity));
  }

  /**
   * This method calls appropriate method on run time based on the
   * parameters (methodName and argName) passed and returns a value
   * returned by that method.
   * @param methodName
   * @param issueKey
   * @return
   */
  public static JsonNode questionMapping(String methodName, String issueKey, JsonNode responseBody) {

    TaskMap taskMap = new TaskMap();

    try {

      //call the method at runtime according to the argument "methodName"
      Method method = TaskMap.class.getMethod(methodName, String.class, JsonNode.class);
      JsonNode returnVal = (JsonNode) method.invoke(taskMap, issueKey, responseBody);
      return returnVal;

    } catch (NoSuchMethodException e) {
      return parseToJson("fail", e.getMessage());
    } catch (InvocationTargetException e) {
      return parseToJson("fail", e.getMessage());
    } catch (IllegalAccessException e) {
      return parseToJson("fail", e.getMessage());
    } catch (NullPointerException e) {
      return parseToJson("fail", e.getMessage());
    }
  }

  /**
   * This method requests issue info and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssuetDescription(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "description").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = "Description of " + issueKey + " is as follows: \n" +
        Extractor.extractString(responseBody, "description");
      return parseToJson("success", answer);
    }

  }

  /**
   * This method requests assignee of issue and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssueAssignee(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "assignee").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = Extractor.extractString(responseBody, "assignee") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method requests breif description of issue and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssueBrief(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "assignee").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = Extractor.extractString(responseBody, "assignee") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method requests status of an issue and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssueStatus(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "status").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = "Status of "+issueKey;//Extractor.extractString(responseBody, "status") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method requests issues that are in progress and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode InProgressIssues(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "status").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = "Status of "+issueKey;//Extractor.extractString(responseBody, "status") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method requests issues that are completed and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode CompletedIssues(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "status").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = "Status of "+issueKey;//Extractor.extractString(responseBody, "status") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method requests issues that are stalled and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode StalledIssues(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "status").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = "Status of "+issueKey;//Extractor.extractString(responseBody, "status") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method sets the project for a channel
   *
   * @param issueKey
   * @return
   */
  public JsonNode SetProject(String issueKey, JsonNode responseBody) {
    if (Extractor.extractString(responseBody, "status").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = "Status of "+issueKey;//Extractor.extractString(responseBody, "status") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method takes String as an input as returns a JSON object in the required format
   *
   * @param message
   * @return
   */
  public static JsonNode parseToJson(String status, String message) {

    Response response = new Response();
    response.status = status;
    response.message = message;
    System.out.println("Response: " + response.message);
    JsonNode answer = Json.toJson(response);

    return answer;

  }
}