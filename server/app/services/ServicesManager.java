package services;

import models.LuisResponse;
import models.ResponseToClient;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.mvc.Result;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.ConfigUtilities;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 * This class contains instances of serviceProviders,
 * each of which provide one set of service.
 * e.g. luisServiceProvider for providing service from LUIS,
 * jiraServiceProvider for providing service from JIRA. etc.
 *
 * @see LuisServiceProvider
 * @see JiraReaderService
 */
public class ServicesManager {

  private LuisServiceProvider luisServiceProvider;
  private JiraServiceProvider jiraServiceProvider;
  private QuestionsDBServiceProvider questionsDBServiceProvider;

  @Inject
  public ServicesManager(WSClient ws) {
    this.luisServiceProvider = new LuisServiceProvider(ws);
    this.jiraServiceProvider = new JiraServiceProvider(ws);
    questionsDBServiceProvider = new QuestionsDBServiceProvider();
  }

  public CompletionStage<Result> interpretQueryAndActOnJira(String query) {
    try {
      //Add question to database
      questionsDBServiceProvider.addQuestion(query);
      LuisResponse luisResponse = luisServiceProvider.interpretQuery(query);

      // reading operations go here
      if (luisResponse.intent.equals("IssueDescription") ||
        luisResponse.intent.equals("IssueAssignee") ||
        luisResponse.intent.equals("IssueStatus")) {
        return jiraServiceProvider.readTicket(luisResponse.intent, luisResponse.entityName);
      } else if (luisResponse.intent.equals("AssigneeIssues")) {
        return jiraServiceProvider.readAssingeeInfo(luisResponse.intent, luisResponse.entityName);
      } else if (luisResponse.intent.equals("AllQuestions")) {
        return CompletableFuture.supplyAsync(() -> ok(Json.toJson(new ResponseToClient("success", questionsDBServiceProvider.getAllStoredQuestions()))));
      } else {
        // TODO: updating operations go here
        throw new NotImplementedException();
      }
    } catch (Exception e) {
      // TODO: questions not understood go here
      return CompletableFuture.supplyAsync(() -> ok(jiraServiceProvider.encodeErrorInJson(
        ConfigUtilities.getString("error-message.luis-error"))));
    }
  }
}
