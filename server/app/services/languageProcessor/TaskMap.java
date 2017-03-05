package services.languageProcessor;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.api.Play;
import play.libs.Json;

import scala.util.parsing.json.JSONArray;
import services.IntentEntity;
import services.Response;
import services.queryHandler.Extractor;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class TaskMap {
  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

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

  public JsonNode IssueDescription(String issueKey, JsonNode responseBody) {
    if (Extractor.getIssueDscription(responseBody, "description").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = "Description of " + issueKey + " is as follows: \n" +
        Extractor.getIssueDscription(responseBody, "description");
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
    if (Extractor.getIssueAssignee(responseBody, "assignee").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = Extractor.getIssueAssignee(responseBody, "assignee") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /**
   * This method requests brief description of issue and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssueBrief(String issueKey, JsonNode responseBody) {
    if (Extractor.getIssueBrief(responseBody, "assignee").equals("[\"Issue Does Not Exist\"]")) {
      return parseToJson("fail", "Cannot find issue");
    } else {
      String answer = Extractor.getIssueBrief(responseBody, "assignee") + " is working on " + issueKey + ".";
      System.out.println(answer);
      return parseToJson("success", answer);
    }
  }

  /** COMPLETE THIS METHOD
   * This method requests status of an issue and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssueStatus(String issueKey, JsonNode responseBody) {

    String answer = "Code to find the STATUS OF AN ISSUE has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**COMPLETE THIS METHOD
   * This method gets the questions that POET was not able to answer in the past.
   *
   * @param issueKey
   * @param responseBody
   * @return
   */
  public JsonNode QuestionsNotAnswered(String issueKey, JsonNode responseBody) {

    String answer = "I have not saved any questions so far. Please wait for my next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /** COMPLETE THIS METHOD
   * This method requests issues that are in progress and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssuesInProgress(String issueKey, JsonNode responseBody) {

    String answer = "Code to find ISSUES IN PROGRESS has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /** COMPLETE THIS METHOD
   * This method requests issues that are completed and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode IssuesCompleted(String issueKey, JsonNode responseBody) {

    String answer = "Code to find COMPLETED ISSUES has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /** COMPLETE THIS METHOD
   * This method requests issues that are stalled and returns it to the calling method
   *
   * @param issueKey
   * @return
   */
  public JsonNode StalledIssues(String issueKey, JsonNode responseBody) {

    String answer = "Code to find STALLED ISSUES has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /** COMPLETE THIS METHOD
   * This method sets the project for a channel
   *
   * @param issueKey
   * @return
   */
  public JsonNode SetProject(String issueKey, JsonNode responseBody) {

    String answer = "Code to SET PROJECT TO A CHANNEL has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /** COMPLETE THIS METHOD
   *
   * This method sets the context for the conversations.
   * It remembers the issue people are talking about.
   * @param issueKey
   * @return
   */
  public JsonNode SetIssueContext(String issueKey, JsonNode responseBody) {

    String answer = "Code to find SET CONTEXT has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method takes String as an input as returns a JSON object in the required format
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