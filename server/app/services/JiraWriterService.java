package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.ResponseToClient;
import play.libs.Json;

// TODO: 1/05/17

/**
 * This class for any operation which makes changes on JIRA, that is, "writing operations".
 * Currently not implemented at all.
 */
public class JiraWriterService {
  /**
   * This method calls appropriate method on run time based on the
   * parameters (methodName and argName) passed and returns a value
   * returned by that method.
   *
   * @param methodName
   * @param issueKey
   * @return
   */
//    public static JsonNode questionMapping(String methodName, String issueKey, JsonNode responseBody) {
//
//        TaskMap taskMap = new TaskMap();
//
//        try {
//            //call the method at runtime according to the argument "methodName"
//            Method method = TaskMap.class.getMethod(methodName, String.class, JsonNode.class);
//
//            return (JsonNode) method.invoke(taskMap, issueKey, responseBody);
//        } catch (NoSuchMethodException
//                | InvocationTargetException
//                | IllegalAccessException
//                | NullPointerException e) {
//            return parseToJson("fail", e.getMessage());
//        }
//    }

  /**
   * This method gets the questions that POET was not able to answer in the past.
   *
   * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return reply to the user in JSON format.
   */
  public JsonNode QuestionsNotAnswered(String issueKey, JsonNode responseBody) {

    String answer = "I have not saved any questions so far. Please wait for my next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method requests issues that are in progress and returns it to the calling method
   *
   * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return reply to the user in JSON format.
   */
  public JsonNode IssuesInProgress(String issueKey, JsonNode responseBody) {

    String answer = "Code to find ISSUES IN PROGRESS has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method requests issues that are completed and returns it to the calling method
   *
   * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return reply to the user in JSON format.
   */
  public JsonNode IssuesCompleted(String issueKey, JsonNode responseBody) {

    String answer = "Code to find COMPLETED ISSUES has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method requests issues that are stalled and returns it to the calling method
   *
   * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return reply to the user in JSON format.
   */
  public JsonNode StalledIssues(String issueKey, JsonNode responseBody) {

    String answer = "Code to find STALLED ISSUES has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method sets the project for a channel
   *
   * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return reply to the user in JSON format.
   */
  public JsonNode SetProject(String issueKey, JsonNode responseBody) {

    String answer = "Code to SET PROJECT TO A CHANNEL has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method sets the context for the conversations.
   * It remembers the issue people are talking about.
   *
   * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return reply to the user in JSON format.
   */
  public JsonNode SetIssueContext(String issueKey, JsonNode responseBody) {

    String answer = "Code to find SET CONTEXT has not been implemented yet. Please wait for our next version update.";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method sends one out of several greeting messages to the user.
   *
   * @param issueKey     is the IssueID of type string which was mentioned in the query by the user.
   * @param responseBody is the JSON object received from JIRA Rest API.
   * @return reply to the user in JSON format.
   */
  public JsonNode Greeting(String issueKey, JsonNode responseBody) {

    String greetings[] = new String[3];
    greetings[0] = "Hi, My name is POET. I can help you get information from JIRA or make modification in JIRA. " +
      "What can I help you with today?";
    greetings[1] = "Hello, I am POET. I am here to help you access information from JIRA or make modification in JIRA. " +
      "What can I help you with today?";
    greetings[2] = "Hi. People call me POET. Not that I write poems. Jokes apart, how can I help you?";

    int index = (int) (Math.random() * 3);

    String answer = greetings[index];

    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method takes String as an input as returns a JSON object in the required format
   *
   * @param message
   * @return
   */
  public static JsonNode parseToJson(String status, String message) {
    ResponseToClient response = new ResponseToClient(status, message);

    System.out.println("ResponseToClient: " + response.message);
    return Json.toJson(response);
  }
}
