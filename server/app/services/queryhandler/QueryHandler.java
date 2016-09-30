package services.queryHandler;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import services.JiraInfo;
import services.Utils;
import services.languageProcessor.Processor;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 * Created by Dongxu on 9/30/2016.
 */
public class QueryHandler {
  private String[] ticketInfoReservedWords = {"description", "assignee"};
  private JiraInfo jiraInfo = Utils.getJiraInfo();

  private String query;
  private WSClient ws;

  CompletionStage<Result> FAILURE = null;

  public QueryHandler(String query, WSClient ws) {
    this.query = query;
    this.ws = ws;
  }

  public CompletionStage<Result> handleQuery(){
    String[] nlpResult;  // {0 :question_mapping; 1:ticket_id}

    try {
      nlpResult = Processor.processQuestion(query);
      return asyncGET(nlpResult[0], nlpResult[1]);
    } catch (Exception e) {
      return FAILURE;
    }
  }

  public CompletionStage<Result> asyncGET(String question_mapping, String ticket_no) throws
    ClassNotFoundException, NoSuchMethodException,
    InvocationTargetException, IllegalAccessException {

    CompletionStage<JsonNode> responsePromise = null;

    if (question_mapping == null){
      Utils.writeMissedQuery(query);
      throw new NullPointerException();
    }

    for(String word : ticketInfoReservedWords){
      if (question_mapping.startsWith(word)){
        responsePromise = getTicketInfo(ticket_no);
      }
    }

    return responsePromise.thenApply(response -> {
        try {
          return ok(processResponse(response, question_mapping, ticket_no));
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      });
  }


  public CompletionStage<JsonNode> getTicketInfo(String ticket_id) {

    String baseUrl = "https://jira.agiledigital.com.au";
    String endPoint = "/rest/api/latest/issue/";

    WSRequest request = ws.url(baseUrl + endPoint + ticket_id);
    WSRequest complexRequest = request.setAuth(jiraInfo.account, jiraInfo.pwd, WSAuthScheme.BASIC);

    return complexRequest.get().thenApply(WSResponse:: asJson);
  }



  private JsonNode processResponse(JsonNode responseBody, String question_mapping, String ticket_id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    String answer = services.languageProcessor.TaskMap.questionMapping(question_mapping, ticket_id);

    // parse the JSON as a JsonNode
    JsonNode json = Json.parse("{\"answer\":\"" +answer+ "\"}");
    return responseBody;
  }

}
