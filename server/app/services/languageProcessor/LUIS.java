package services.languageProcessor;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.api.Play;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import services.JiraInfo;
import services.Utils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 * Created by sabinapokhrel on 11/21/16.
 */
public class LUIS {

  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);
  private JiraInfo jiraInfo = Utils.getJiraInfo(configuration);

  private String query;
  private WSClient ws;


  public LUIS(String query, WSClient ws) {
    this.query = query;
    this.ws = ws;
  }

  public CompletionStage<Result> handleQuery(){

    return asyncGET(query);
  }


  public CompletionStage<JsonNode> getTask(String query) {

    String luisurl = configuration.getString("luis.url");
    String appid = configuration.getString("luis.appId");
    String key = configuration.getString("luis.subscription-key");

    WSRequest request = ws.url(luisurl);

    WSRequest complexRequest = request.setQueryParameter("subscription-key",key).setQueryParameter("q",query);

    return complexRequest.get().thenApply(WSResponse:: asJson);
  }

  private JsonNode processResponse(JsonNode responseBody){

    JsonNode jsonNode = null;
    try {
      jsonNode = services.languageProcessor.TaskMap.questionMapping(responseBody);
    } catch (Exception e){
      e.getMessage();
    }

    return jsonNode;
  }



  public CompletionStage<Result> asyncGET(String query) {

    CompletionStage<JsonNode> responsePromise = getTask(query);

    return responsePromise.thenApply(response -> ok(processResponse(response)));
  }

}