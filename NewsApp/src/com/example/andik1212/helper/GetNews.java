package com.example.andik1212.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Андрей
 * Date: 13.11.12
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class GetNews extends Thread{
    private JSONArray jNewsArray = null;
    private static String answer;
    private static String error;
    public static Boolean finished = false;
//    public GetNews(JSONArray jArray){
//        this.jNewsArray=jArray;
//    }
    private ArticleCollection localArticles;
//    public GetNews(ArticleCollection artColl){
//        localArticles=artColl;
//    }


    public void run(){
        String data = this.load();

        try {
            JSONObject jo = new JSONObject(data);
            localArticles = new ArticleCollection().fromJson(jo);
            finished = true;
            } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    public ArticleCollection returner(){
            return localArticles;
    }
    protected static String load(){
        URL url = null;

        try {
            url = new URL("http://android-developers.blogspot.com/feeds/posts/default?alt=json");
            URLConnection conn = null;
            conn = url.openConnection();
//            HttpConnectionParams.setConnectionTimeout((HttpParams) conn, 200/*CONNECTION_TIMEOUT*/);
//            HttpConnectionParams.setSoTimeout((HttpParams) conn, 200/*SOCKET_TIMEOUT*/);
            InputStreamReader rd = new InputStreamReader(conn.getInputStream());
            StringBuilder allPage = new StringBuilder();
            int n = 0;
            char[] buffer = new char[40000];
            while (n >= 0){
                n = rd.read(buffer, 0, buffer.length);
                if (n > 0){
                    allPage.append(buffer, 0, n);
                }
            }
            answer=allPage.toString();
        }
        catch (IOException e){
            Log.e("Error : ", "Error on soapPrimitiveData() " + e.getMessage());
            e.printStackTrace();

        }
        return answer;
    }



//    public static String load() throws IOException {
//        StringBuilder builder = new StringBuilder();
//        HttpClient client;
//        client = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet("http://android-developers.blogspot.com/feeds/posts/default?alt=json");
//
//        HttpResponse response = client.execute(httpGet);
//        StatusLine statusLine = response.getStatusLine();
//        int statusCode = statusLine.getStatusCode();
//        if (statusCode == 200) {
//            HttpEntity entity = response.getEntity();
//            InputStream content = entity.getContent();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                builder.append(line);
//            }
//        }
//
//        return builder.toString();
//    }
}
