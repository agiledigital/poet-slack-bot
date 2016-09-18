package controllers;

import play.mvc.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Application extends Controller {

    public Result index() {
        return ok("Hi!");
    }

    public Result QA(String question) throws IOException,
            ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException,IllegalAccessException{

        String answer = services.languageProcessor.Processor.processQuestion(question);

        return ok(answer);
    }

}