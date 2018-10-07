package com.jinojino.klashelper.Thread;


import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpThread extends Thread{
    String result;
    String id;
    String pw;
    String json;


    public void run(){
        try {
            HttpResponse response;
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", id);
            jsonObject.accumulate("pw", pw);
            json = jsonObject.toString();
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://175.195.89.200:9999/get_ass");
            httpPost.setEntity(new StringEntity(json, "UTF-8"));
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept-Encoding", "application/json");
            httpPost.setHeader("Accept-Language", "ko");
            response = httpClient.execute(httpPost);
            String sresponse = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            result = sresponse;
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());

        } finally {
            /* nothing to do here */
        }

    }

   public String getResult(){
        return result;
   }

    public HttpThread(String id, String pw){
        this.id = id;
        this.pw = pw;
    }

}
