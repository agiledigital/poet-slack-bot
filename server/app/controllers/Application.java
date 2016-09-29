package controllers;

import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import services.JiraInfo;
import services.Utils;
import services.languageProcessor.Processor;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletionStage;

public class Application extends Controller {

  @Inject
  WSClient ws;

  private JiraInfo jiraInfo = Utils.getJiraInfo();

  public Result index() {
    return ok("Hi!");
  }

  public CompletionStage<Result> QA(String question) throws IOException,
    ClassNotFoundException, NoSuchMethodException,
    InvocationTargetException, IllegalAccessException {

    String question_mapping = Processor.processQuestion(question)[0];
    String ticket_id = Processor.processQuestion(question)[1];

    CompletionStage<WSResponse> responsePromise = null;

    if( question_mapping.startsWith("description") || question_mapping.startsWith("assignee")){
        responsePromise =  getTicketInfo(ticket_id);
    } else if (false){}
    else {}

    CompletionStage<Result> promiseOfResult = responsePromise.thenApply(response -> {
        try {
          return ok(processResponse(response.getBody(), question_mapping, ticket_id));
        } catch (Exception e) { e.printStackTrace();}
      return null;
      }
    );

    return promiseOfResult;
  }

  public CompletionStage<WSResponse> getTicketInfo(String ticket_id) {

    WSRequest request = ws.url("https://jira.agiledigital.com.au/rest/api/latest/issue/" + ticket_id);
    WSRequest complexRequest = request.setAuth(jiraInfo.account, jiraInfo.pwd, WSAuthScheme.BASIC);

    return complexRequest.get();
  }

  private JsonNode processResponse(String responseBody, String question_mapping, String ticket_id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    String answer = services.languageProcessor.TaskMap.questionMapping(question_mapping, ticket_id);

    // parse the JSON as a JsonNode
    JsonNode json = Json.parse("{\"answer\":\"" +answer+ "\"}");

    return Json.parse(responseBody);
  }
}