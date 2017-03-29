package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.api.Play;
import play.libs.Json;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import services.models.JiraUserCredentials;
import services.models.BotResponse;
import services.Utils;
import services.languageProcessor.Processor;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

public class QueryHandler {
  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);
  private JiraUserCredentials jiraUserCredentials = Utils.getJiraInfo(configuration);

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

  public CompletionStage<Result> asyncGET(String questionMapping, String ticketNo) {

    CompletionStage<JsonNode> responsePromise = getTicketInfo(ticketNo);

    if(ticketNo == "NoIdFound") {
      return responsePromise.thenApply(response -> ok(parseErrorToJson("No id Found")));
    }

    return responsePromise.thenApply(response -> {
      if(questionMapping.equals("getQuestions")){
        System.out.println("Displaying all questions in DB");
        return ok(processResponse(questionMapping));
      }
      else if(questionMapping != "NoQuestionFound") {
        return ok(processResponse(response, questionMapping, ticketNo));
      }
      else {
        return ok(parseErrorToJson(configuration.getString("error-message.invalid-question")));
      }
    });
  }

  public CompletionStage<JsonNode> getTicketInfo(String ticketId) {

    String[] requestConfig = configTicketRequest();

    WSRequest request = ws.url(requestConfig[0] + requestConfig[1] + ticketId);
    WSRequest complexRequest = request.setAuth(jiraUserCredentials.username, jiraUserCredentials.password, WSAuthScheme.BASIC);

    return complexRequest.get().thenApply(WSResponse:: asJson);
  }

  public String [] configTicketRequest(){
    String[] requestConfig = new String[2];
    requestConfig[0] = configuration.getString("jira.baseUrl");
    requestConfig[1] = configuration.getString("jira.issueEndpoint");

    return requestConfig;
  }

  private JsonNode processResponse(JsonNode responseBody, String questionMapping, String ticketId){

    JsonNode jsonNode = null;
    try {
      jsonNode = services.languageProcessor.TaskMap.questionMapping(questionMapping, ticketId, responseBody);
    } catch (Exception e){
      e.getMessage();
    }

    return jsonNode;
  }

  private JsonNode processResponse(String questionMapping){

    JsonNode jsonNode = null;
    try {
      jsonNode = services.languageProcessor.TaskMap.questionMapping(questionMapping);
    } catch (Exception e){
      e.getMessage();
    }

    return jsonNode;
  }

  public JsonNode parseErrorToJson(String message) {

    BotResponse botResponse = new BotResponse();
    botResponse.status = "fail";
    botResponse.message = message;

    JsonNode msg = Json.toJson(botResponse);

    return msg;
  }


}