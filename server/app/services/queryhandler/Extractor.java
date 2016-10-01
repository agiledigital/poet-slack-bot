package services.queryhandler;

import com.typesafe.config.ConfigException;
import play.libs.ws.WSAuthScheme;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import services.JiraInfo;
import services.Utils;
//import services.httpclient.JIRAconnector;
import services.languageProcessor.Processor;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletionStage;


/**
 * Created by wangxu on 30/09/2016.
 */
//nafsdafsa

public class Extractor {
    
    public static JsonNode extractJson(JsonNode json, String key){


        if(json.get("errorMessages") != null){
             return json.get("errorMessages");
        }

        else {

            if (key == "description"){
                return json.get("fields").get(key);
            }else if (key == "assignee") {
                return json.get("assignee").get("name");
            }
        }

        return null;
    }


    public static String extractString(JsonNode json, String key){


        if(json.get("errorMessages") != null){
            return json.get("errorMessages").toString();
        }

        else{

            if (key == "description"){
                return json.get("fields").get(key).toString();
            } else if (key == "assignee") {
                return json.get("assignee").get("name").toString();
            }
        }

        return "";
    }
}
