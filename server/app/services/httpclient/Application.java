package services.httpclient;

import java.io.*;
import java.net.*;

import javax.inject.Inject;

import play.mvc.*;
import play.libs.ws.*;
import java.util.concurrent.CompletionStage;







public class JIRAconnector extends Controller{
    @Inject WSClient ws;
    /*
    public static String temp(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        String userPassword = "xuwang" + ":" + "woshishui";
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());



        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization", "Basic " + encoding);
        conn.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }*/


    public Result temp(){


       // WSRequest complexRequest = ws.url("jira.agiledigital.com.au/rest/api/latest/issue/POET-3").setAuth("xuwang", "woshishui", WSAuthScheme.BASIC);
        //CompletionStage<WSResponse> responsePromise = complexRequest.get();


        WSRequest request = ws.url("https://jira.agiledigital.com.au/rest/api/latest/issue/POET-3");
        //WSRequest request = ws.url("http://google.com");
        WSRequest complexRequest = request.setAuth("xuwang", "woshishui", WSAuthScheme.BASIC);

        //CompletionStage<WSResponse> request = ws.url("jira.agiledigital.com.au/rest/api/latest/issue/POET-3").setAuth("xuwang", "woshishui", WSAuthScheme.BASIC).get();

        CompletionStage<WSResponse> responsePromise = complexRequest.get();
        //play.libs.F.Promise<WSResponse> responsePromise = complexRequest.get();

        //CompletionStage<WSResponse> responsePromise = complexRequest.get();

        return ok("Hello to you all");

        //String ans  = responsePromise.toString();
       // String ans = "12";




        //System.out.println("\n\n\n\nThis is the answers ==========\n" + ans +"\n\n\n\n\n");
        //return "";
    }


    /*public static void main(String[] args) {
        JIRAconnector jirAconnector = new JIRAconnector();
        jirAconnector.temp();
    }
    */


}
