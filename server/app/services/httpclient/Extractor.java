package services.httpclient;

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


public class Extractor {
    
    public static JsonNode extract(JsonNode json, String key){
        JsonNode result = null;
        if(json.get(key) == null){
            result = json.get("errorMessages");
            return result;
        }
        else {
            result = json.get(key);
            return result;
        }
    }
}
