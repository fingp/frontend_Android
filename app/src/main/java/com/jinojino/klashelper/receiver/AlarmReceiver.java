package com.jinojino.klashelper.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.jinojino.klashelper.R;
import com.jinojino.klashelper.activity.MainActivity;
import com.jinojino.klashelper.service.AlarmService;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mServiceIntent = new Intent(context, AlarmService.class);
        mServiceIntent.putExtras(intent.getExtras());
        mServiceIntent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        context.startForegroundService(mServiceIntent);
    }
}
