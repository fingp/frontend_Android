package com.jinojino.klashelper.Thread;


import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static android.content.ContentValues.TAG;


public class ServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;
    String result;
    int id;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
    }
    public String getResult(){
        return result;
    }
}
