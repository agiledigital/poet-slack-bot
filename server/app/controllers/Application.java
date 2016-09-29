package controllers;

import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletionStage;

public class Application extends Controller {

  @Inject
  WSClient ws;

  public Result index() {
    return ok("Hi!");
  }

  public CompletionStage<Result> QA(String question) throws IOException,
    ClassNotFoundException, NoSuchMethodException,
    InvocationTargetException, IllegalAccessException {

    String answer = services.languageProcessor.Processor.processQuestion(question);

    WSRequest request = ws.url("https://jira.agiledigital.com.au/rest/api/latest/issue/POET-3");
    WSRequest complexRequest = request.setAuth("xuwang", "woshishui", WSAuthScheme.BASIC);

    CompletionStage<WSResponse> responsePromise = complexRequest.get();
    CompletionStage<Result> promiseOfResult = responsePromise.thenApply(pi ->
      ok("PI value computed: " + pi.getBody())
    );


/%
    %/

    // parse the JSON as a JsonNode
    JsonNode json = Json.parse("{\"answer\":\"" +answer+ "\"}");

    return promiseOfResult;
    //return ok(json);
  }

}