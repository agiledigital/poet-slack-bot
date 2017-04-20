package services;

import models.LuisResponse;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.mvc.Result;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.ConfigUtilities;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

/**
 *  This class contains instances of serviceProviders,
 *  each of which provide one set of service.
 *  e.g. luisServiceProvider for providing service from LUIS,
 *       jiraServiceProvider for providing service from JIRA. etc.
 *
 *  @see LuisServiceProvider
 *  @see JiraReaderService
 */
public class ServicesManager {

    private LuisServiceProvider luisServiceProvider;
    private JiraServiceProvider jiraServiceProvider;


    @Inject
    public ServicesManager(WSClient ws) {
        this.luisServiceProvider = new LuisServiceProvider(ws);
        this.jiraServiceProvider = new JiraServiceProvider(ws);
    }

    public CompletionStage<Result> interpretQueryAndActOnJira(String query) {
        try {
            LuisResponse luisResponse = luisServiceProvider.interpretQuery(query);
            // reading operations go here
            if (luisResponse.intent.equals("IssueBrief") ||
              luisResponse.intent.equals("IssueAssignee") ||
              luisResponse.intent.equals("IssueStatus")) {
                return jiraServiceProvider.readTicket(luisResponse.intent, luisResponse.entityName);
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
