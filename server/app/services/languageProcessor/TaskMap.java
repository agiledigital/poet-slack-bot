package services.languageProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.Json;

import services.Response;
import services.queryhandler.Extractor;

import java.io.IOException;
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
  public static JsonNode questionMapping(String methodName, String argName, JsonNode responseBody) throws NoSuchMethodException,
    InvocationTargetException,IllegalAccessException{

    TaskMap tm = new TaskMap();

    //call the method at runtime according to the argument "methodName"
    Method m = TaskMap.class.getMethod(methodName, String.class, JsonNode.class);
    JsonNode returnVal = (JsonNode) m.invoke(tm, argName, responseBody);
    return returnVal;
  }

  /**
   * This method requests ticket info and returns it to the calling method
   * @param ticket
   * @return
   */
  public JsonNode description_of_ticket(String ticket, JsonNode responseBody){
    String answer = Extractor.extractString(responseBody, "description");

    System.out.println("---------");
    System.out.println(answer);
    return parseToJson(answer);
  }

  /**
   * This method requests assignee of ticket and returns it to the calling method
   * @param ticket
   * @return
   */
  public JsonNode assignee_of_ticket(String ticket, JsonNode responseBody){
    String answer = Extractor.extractString(responseBody, "assignee") + " is working on "+ ticket;
    return parseToJson(answer);
  }

  /**
   * This method takes String as an input as returns a JSON object in the required format
   * @param answer
   * @return
   */
  public JsonNode parseToJson(String answer){

      Response response = new Response();
      response.status = "success";
      response.answer = answer;

      JsonNode ans = Json.toJson(response);

      return ans;

  }
}