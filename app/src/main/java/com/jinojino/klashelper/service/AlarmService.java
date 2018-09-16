package com.jinojino.klashelper.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;

import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import android.widget.Toast;

import com.jinojino.klashelper.R;
import com.jinojino.klashelper.Thread.ServiceThread;
import com.jinojino.klashelper.activity.MainActivity;


public class AlarmService extends Service {
    NotificationManager Notifi_M;
    Notification Notifi;
    ServiceThread thread;

    String workCourse;
    String workTitle;
    String workCode;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        workCourse = intent.getExtras().getString("workCourse");
        workTitle = intent.getExtras().getString("workTitle");
        workCode = intent.getExtras().getString("workCode");
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_NOT_STICKY;
    }
        class myServiceHandler extends Handler {
            @Override
            public void handleMessage(android.os.Message msg) {
                // The id of the channel.
                String idd = "my_channel_01";

                // The user-visible name of the channel.
                CharSequence name = getString(R.string.app_name);

                // The user-visible description of the channel.
                String description = "ddd";

                int importance = NotificationManager.IMPORTANCE_LOW;

                NotificationChannel mChannel = new NotificationChannel(idd, name, importance);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                Notifi_M.createNotificationChannel(mChannel);

                Intent intent = new Intent(AlarmService.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(AlarmService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Notifi = new NotificationCompat.Builder(getApplicationContext())
                            .setContentTitle(workCourse)
                            .setContentText(workTitle + " 알림")
                            .setSmallIcon(R.drawable.ic_home_black_24dp)
                            .setTicker("알림!!!")
                            .setContentIntent(pendingIntent)
                            .setChannelId(idd)
                            .build();

                    //소리추가
                    Notifi.defaults = Notification.DEFAULT_SOUND;

                    //알림 소리를 한번만 내도록
                    Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

                    //확인하면 자동으로 알림이 제거 되도록
                    Notifi.flags = Notification.FLAG_AUTO_CANCEL;

                    startForeground(1, Notifi);
                }
        };
}



