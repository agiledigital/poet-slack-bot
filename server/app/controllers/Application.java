package controllers;

import play.mvc.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Application extends Controller {

    public Result index() throws IOException,
            ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException,IllegalAccessException{

        String answer = services.languageProcessor.Processor.processQuestion("What is POET-1?");

        return ok(answer);
    }

}
