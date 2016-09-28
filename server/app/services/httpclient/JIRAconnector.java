package services.httpclient;

import java.io.*;
import java.net.*;

import javax.inject.Inject;

import play.mvc.*;
import play.libs.ws.*;
import java.util.concurrent.CompletionStage;





public class JIRAconnector extends Controller{

    @Inject WSClient ws;

    public String temp(){


        WSRequest complexRequest = ws.url("jira.agiledigital.com.au/rest/api/latest/issue/POET-3").setAuth("xuwang", "woshishui", WSAuthScheme.BASIC);
        CompletionStage<WSResponse> responsePromise = complexRequest.get();
        //System.out.println('1');
        //System.out.println(responsePromise);
        String ans  = responsePromise.toString();
        return ans;
    }


    //public static void main(String[] args) {
      //  JIRAconnector jirAconnector = new JIRAconnector();
        //jirAconnector.temp();
    //}


}
