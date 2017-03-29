package controllers;

import play.libs.ws.WSClient;
import play.mvc.*;
import services.ServicesManager;

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

    public CompletionStage<Result> show(String query)
            throws IOException, ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {

        ServicesManager servicesManager = new ServicesManager(query, ws);
        return servicesManager.interpretQueryAndActOnJira();
    }
}