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
import services.languageProcessor.Processor;

import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

public class JiraApiFetchInfo {
  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);
  private JiraInfo jiraInfo = Utils.getJiraInfo(configuration);

  private String query;
  private WSClient ws;


  public JiraApiFetchInfo(String query, WSClient ws) {
    this.query = query;
    this.ws = ws;
  }

  public CompletionStage<Result> handleQuery(String method, String issueId){

    return asyncGET(method, issueId);
  }

  /**
   *
   * @param questionMapping
   * @param issueId
   * @return
   */
  public CompletionStage<Result> asyncGET(String questionMapping, String issueId) {

    CompletionStage<JsonNode> responsePromise = getTicketInfo(issueId);

    return responsePromise.thenApply(response -> ok(processResponse(response, questionMapping, issueId)));
  }

  /**
   * This method is used to connect to JIRA using RestApi.
   * It uses the JIRA username and password set in configuration file
   * for authentication.
   * @param ticketId
   * @return
   */
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

  /**
   * This method calls task mapping method in order to perform the intended actions
   * @param responseBody
   * @param questionMapping
   * @param ticketId
   * @return
   */
  private JsonNode processResponse(JsonNode responseBody, String questionMapping, String ticketId){

    JsonNode jsonNode = null;
    try {
      jsonNode = services.languageProcessor.TaskMap.questionMapping(questionMapping, ticketId, responseBody);
    } catch (Exception e){
      e.getMessage();
    }

    return jsonNode;
  }
}