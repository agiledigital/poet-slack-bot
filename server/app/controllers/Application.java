package controllers;

import play.mvc.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Application extends Controller {

  public Result index() {
    return ok("Hi!");
  }

  public Result QA(String question) throws IOException,
    ClassNotFoundException, NoSuchMethodException,
    InvocationTargetException, IllegalAccessException {

    String answer = services.languageProcessor.Processor.processQuestion(question);

    // parse the JSON as a JsonNode
    JsonNode json = Json.parse("{\"answer\":\"" +answer+ "\"}");

    return ok(json);
  }

}