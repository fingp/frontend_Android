package com.jinojino.klashelper.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.jinojino.klashelper.DB.DBHelper;
import com.jinojino.klashelper.R;
import com.jinojino.klashelper.Thread.ServiceThread;
import com.jinojino.klashelper.activity.MainActivity;


public class AlarmService extends Service {
    NotificationManager Notifi_M;
    Notification Notifi;
    Notification Notifi1;
    ServiceThread thread;

    String workCourse;
    String workTitle;
    String workCode;
    String alarmTitle;

    DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        Notifi1 = new NotificationCompat.Builder(getApplicationContext()).build();
        startForeground(1, Notifi1);
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
        dbHelper= new DBHelper(getApplicationContext(), "Work.db", null, 1);

        workCourse = intent.getExtras().getString("workCourse");
        workTitle = intent.getExtras().getString("workTitle");
        workCode = intent.getExtras().getString("workCode");
        alarmTitle = intent.getExtras().getString("alarmTitle");

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

                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel mChannel = new NotificationChannel(idd, name, importance);
                mChannel.setLightColor(Color.RED);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.enableVibration(true);
                Notifi_M.createNotificationChannel(mChannel);

                Intent intent = new Intent(AlarmService.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(AlarmService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notifi = new NotificationCompat.Builder(getApplicationContext(), idd)
                        .setContentTitle(workCourse)
                        .setContentText(workTitle + " "+ alarmTitle + " 알림")
                        .setSmallIcon(R.drawable.ic_home_black_24dp)
                        .setTicker("알림!!!")
                        .setContentIntent(pendingIntent)
                        .setChannelId(idd)
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .build();

                //알림 소리를 한번만 내도록
                Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                Notifi.flags |= Notification.FLAG_AUTO_CANCEL;
                int temp = dbHelper.addNotify(0);
                Notifi_M.notify(temp, Notifi);
                }
        };
}



