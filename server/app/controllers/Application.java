package controllers;

import play.libs.ws.WSClient;
import play.mvc.*;

import services.languageProcessor.LUIS;
import services.queryHandler.QueryHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletionStage;

public class Application extends Controller {

  @Inject
  WSClient ws;

  public Result index() {
    return ok("Hi!");
  }

  public CompletionStage<Result> getAnwser(String query) throws IOException,
    ClassNotFoundException, NoSuchMethodException,
    InvocationTargetException, IllegalAccessException {


    LUIS queryHandler = new LUIS(query, ws);
    return queryHandler.handleQuery();
  }
}