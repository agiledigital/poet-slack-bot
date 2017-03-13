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
import services.IntentEntity;
import services.JiraInfo;
import services.Response;
import services.Utils;
import services.languageProcessor.LUIS;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

import static play.mvc.Results.ok;

public class QueryHandler {
  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);
  private JiraInfo jiraInfo = Utils.getJiraInfo(configuration);

  private String query;
  private WSClient ws;


  public QueryHandler(String query, WSClient ws) {
    this.query = query;
    this.ws = ws;
  }


  public CompletionStage<Result> handleQuery(){

    //Call luis method that processes query
    LUIS luis = new LUIS(query, ws);
    try {
      IntentEntity intentEntity = luis.handleQuery();
      String intent = intentEntity.intent;
      String entityName = intentEntity.entityName;
      return asyncGET(intent, entityName);
    }catch(Exception e){
      return CompletableFuture.supplyAsync(() -> ok(parseErrorToJson("LUIS is not configured.")));
    }
  }

  public CompletionStage<Result> asyncGET(String questionMapping, String ticketNo) {

    CompletionStage<JsonNode> responsePromise = getTicketInfo(ticketNo);

    if(ticketNo == "NoIdFound") {
      return responsePromise.thenApply(response -> ok(parseErrorToJson("No id Found")));
    }

    return responsePromise.thenApply(response -> {
      if(questionMapping != "NoQuestionFound") {
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
    WSRequest complexRequest = request.setAuth(jiraInfo.username, jiraInfo.password, WSAuthScheme.BASIC);

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

  public JsonNode parseErrorToJson(String message) {

    Response response = new Response();
    response.status = "fail";
    response.message = message;

    JsonNode msg = Json.toJson(response);

    return msg;
  }


}