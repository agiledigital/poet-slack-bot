package services.httpclient;

import java.io.*;
import java.net.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.Gson;



public class JIRAconnector{

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        String userPassword = "xuwang" + ":" + "woshishui";
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
        Gson gson = new Gson();


        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();


        conn.setRequestProperty("Authorization", "Basic " + encoding);
        conn.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        //System.out.println(result);
        rd.close();

        return result.toString();
    }

    public static String getDescription(String id)throws Exception{
        Gson gson = new Gson();
        String result = getHTML("https://jira.agiledigital.com.au/rest/api/latest/issue/" + id);
        Tickets tickets = gson.fromJson(result,Tickets.class);

        String description = tickets.getFields().getIssuetype().getDescription();

        return description;
    }

    public static String getAssignee(String id) throws  Exception{
        Gson gson = new Gson();
        String result = getHTML("https://jira.agiledigital.com.au/rest/api/latest/issue/" + id);
        Tickets tickets = gson.fromJson(result,Tickets.class);

        String assignee = tickets.getFields().getAssignee().getName();

        return assignee;

    }


}
