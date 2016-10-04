package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.api.Application;
import play.api.Play;
import play.libs.Json;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import services.JiraInfo;
import services.Response;
import services.Utils;
import services.languageProcessor.Processor;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

public class QueryHandler {
  private JiraInfo jiraInfo = Utils.getJiraInfo();
  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

  private String query;
  private WSClient ws;


  public QueryHandler(String query, WSClient ws) {
    this.query = query;
    this.ws = ws;
  }

  public CompletionStage<Result> handleQuery(){
    String[] nlpResult = {};  // {0 :question_mapping; 1:ticket_id}

    try {
      nlpResult = Processor.processQuestion(query);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }

    return asyncGET(nlpResult[0], nlpResult[1]);
  }

  public CompletionStage<Result> asyncGET(String question_mapping, String ticket_no) {

    CompletionStage<JsonNode> responsePromise = getTicketInfo(ticket_no);

    if(ticket_no == "NoIdFound") {
      return responsePromise.thenApply(response -> ok(parseErrorToJson("No id Found")));
    }

    return responsePromise.thenApply(response -> {
      if(question_mapping != "NoQuestionFound") {
        return ok(processResponse(response, question_mapping, ticket_no));
      }
      else {
        return ok(parseErrorToJson("Cannot understand the question"));
      }
    });
  }

  public CompletionStage<JsonNode> getTicketInfo(String ticket_id) {

    String baseUrl = configuration.getString("jira.baseUrl");
    String endPoint = configuration.getString("jira.endPoint_ticket");

    WSRequest request = ws.url(baseUrl + endPoint + ticket_id);
    WSRequest complexRequest = request.setAuth(jiraInfo.account, jiraInfo.pwd, WSAuthScheme.BASIC);

    return complexRequest.get().thenApply(WSResponse:: asJson);
  }

  private JsonNode processResponse(JsonNode responseBody, String question_mapping, String ticket_id){

    JsonNode jsonNode = null;
    try {
      jsonNode = services.languageProcessor.TaskMap.questionMapping(question_mapping, ticket_id, responseBody);
    } catch (Exception e){
      e.getMessage();
    }

    System.out.println("JSON Val: Status: "+ jsonNode.get("status")+ " Message: "+jsonNode.get("message"));
    return jsonNode;

  }

  public JsonNode parseErrorToJson(String message) {

    Response response = new Response();
    response.status = "fail";
    response.message = message;

    JsonNode msg = Json.toJson(response);

    System.out.println("JSON Val: Status: "+ msg.get("status")+ " Message: "+msg.get("message"));

    return msg;
  }


}