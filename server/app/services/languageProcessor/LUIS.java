package services.languageProcessor;

import com.fasterxml.jackson.databind.JsonNode;
import play.Configuration;
import play.api.Play;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import services.IntentEntity;
import services.queryHandler.JiraApiFetchInfo;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 * Created by sabinapokhrel on 11/21/16.
 */
public class LUIS {

  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

  private String query;
  private WSClient ws;


  public LUIS(String query, WSClient ws) {
    this.query = query;
    this.ws = ws;
  }

  public JsonNode handleQuery(){

    return asyncGET(query);
  }

  /**
   *
   * @param query
   * @return
   */
  public JsonNode asyncGET(String query) {

    CompletionStage<JsonNode> responsePromise = getTask(query);
    System.out.println("LUIS asyncGet: ");
    System.out.println(responsePromise.toString());
    try{
      JsonNode node = responsePromise.toCompletableFuture().get();
      return taskMapping(node);
    }catch(Exception e){
      System.out.println("Exception: " + e.toString());
    }
    return null;
    //return responsePromise.thenApply(response -> ok(taskMapping(response)));
  }

  /**
   * Connects to LUIS API using HTTPS request.
   * LUIS API processes the query and returns intent and entities as JSON object
   * @param query
   * @return
   */
  public CompletionStage<JsonNode> getTask(String query) {

    String luisurl = configuration.getString("luis.url");
    String appid = configuration.getString("luis.appId");
    String key = configuration.getString("luis.subscription-key");

    WSRequest request = ws.url(luisurl);

    WSRequest complexRequest = request.setQueryParameter("subscription-key",key).setQueryParameter("q",query);

    return complexRequest.get().thenApply(WSResponse:: asJson);
  }


  /**
   * This method gets the topScoring intent and identified entities
   * from LUIS API and calls methods to perform the required action.
   * @param responseBody
   * @return
   */
  public JsonNode taskMapping(JsonNode responseBody) {
    System.out.print((responseBody.toString()));
    String topScoringIntent = responseBody.get("topScoringIntent").get("intent").toString().replace("\"", "");

    String entity = null;
    if(responseBody.get("entities").findValues("entity").size() != 0)
      entity = responseBody.get("entities").findValues("entity").get(0).toString().replaceAll("\"", "").replaceAll("\\s","");

    String entityType = null;
    if(responseBody.get("entities").findValues("type").size() != 0)
      entityType = responseBody.get("entities").findValues("type").get(0).toString().replace("\"", "").replace(" ","");

    IntentEntity intentEntity = new IntentEntity();
    intentEntity.intent = topScoringIntent;
    intentEntity.entityType = entityType;
    intentEntity.entityName = entity;

    /*
    JiraApiFetchInfo jiraApiFetchInfo = new JiraApiFetchInfo(query, ws);

    if(entityType == null) {
      jiraApiFetchInfo.handleQuery(topScoringIntent, "POET-0");
    }
    else {
      switch (entityType) {
        case "IssueID":
          jiraApiFetchInfo.handleQuery(topScoringIntent, entity);
          break;
        case "ProjectID":
          break;
        default:
          jiraApiFetchInfo.handleQuery(topScoringIntent, "POET-0");
          break;
      }
    }
    */

    return Json.toJson((intentEntity));
  }
}