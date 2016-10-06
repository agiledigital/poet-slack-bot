package services.languageProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import services.Response;
import services.queryHandler.Extractor;

import java.lang.reflect.*;

public class TaskMap{

  /**
   * This method calls appropriate method on run time based on the
   * parameters (methodName and argName) passed and returns a value
   * returned by that method.
   * @param methodName
   * @param argName
   * @return
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  final static String ERROR_MSG = "Sorry, I cannot understand the question";

  public static JsonNode questionMapping(String methodName, String argName, JsonNode responseBody){

    TaskMap taskMap = new TaskMap();

    try {

      //call the method at runtime according to the argument "methodName"
      Method m = TaskMap.class.getMethod(methodName, String.class, JsonNode.class);
      JsonNode returnVal = (JsonNode) m.invoke(taskMap, argName, responseBody);
      return returnVal;

    }
    catch (NoSuchMethodException e)
    {
      e.printStackTrace();
      return taskMap.parseToJson("fail",ERROR_MSG);
    }
    catch (InvocationTargetException e){
      e.printStackTrace();
      return taskMap.parseToJson("fail",ERROR_MSG);
    }
    catch (IllegalAccessException e){
      e.printStackTrace();
      return taskMap.parseToJson("fail",ERROR_MSG);
    }
    catch (NullPointerException e){
      e.printStackTrace();
      return taskMap.parseToJson("fail",ERROR_MSG);
    }
  }

  /**
   * This method requests ticket info and returns it to the calling method
   * @param ticketKey
   * @return
   */
  public JsonNode getTicketDescription(String ticketKey, JsonNode responseBody){
    String answer = "Description of " + ticketKey + " is as follows: \n" +
      Extractor.extractString(responseBody, "description");
    return parseToJson("success", answer);
  }

  /**
   * This method requests assignee of ticket and returns it to the calling method
   * @param ticketKey
   * @return
   */
  public JsonNode getTicketAssignee(String ticketKey, JsonNode responseBody){
    String answer = Extractor.extractString(responseBody, "assignee") + " is working on " + ticketKey + ".";
    System.out.println(answer);
    return parseToJson("success", answer);
  }

  /**
   * This method takes String as an input as returns a JSON object in the required format
   * @param message
   * @return
   */
  public static JsonNode parseToJson(String status, String message){

      Response response = new Response();
      response.status = status;
      response.message = message;
      System.out.println("Response: "+response.message);
      JsonNode answer = Json.toJson(response);

      return answer;

  }
}