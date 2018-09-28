package com.jinojino.klashelper.Thread;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jinojino.klashelper.DB.DBHelper;
import com.jinojino.klashelper.java.Work;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HttpThread extends Thread{
    String result;
    String id;
    String pw;
    String json;
    DBHelper dbHelper;
    ArrayList<Work> worklist;


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
