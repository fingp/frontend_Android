package com.jinojino.klashelper.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jinojino.klashelper.R;
import com.jinojino.klashelper.receiver.AlarmReceiver;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmSettingActivity extends AppCompatActivity {

    String workCourse;
    String workTitle;
    String workCode;
    DatePicker datePicker;
    TimePicker timePicker;
    static int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        Intent intent = getIntent();
        workCourse = intent.getExtras().getString("workCourse");
        workTitle = intent.getExtras().getString("workTitle");
        workCode = intent.getExtras().getString("workCode");

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.time_picker);
        Button button = (Button)findViewById(R.id.setButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year, month, day;
                int hour, min;

                year = datePicker.getYear();
                month = datePicker.getMonth();
                day = datePicker.getDayOfMonth();
                hour = timePicker.getHour();
                min = timePicker.getMinute();

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
                calendar.set(Calendar.SECOND, 0);

                long now = System.currentTimeMillis();
                long bTime = calendar.getTimeInMillis();

                Date date = new Date(now);
                Date date1 = new Date(bTime);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String getTime = sdf.format(date);
                String getTime2 = sdf.format(date1);
                Date date2;
                try{
                    date2 = sdf.parse(getTime2);
                    calendar.setTime(date2);
                }catch (Exception e){
                    Log.d("오류", "오류발생");
                }

                long cTime = calendar.getTimeInMillis();

                AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                intent.putExtra("workCode", workCode);
                intent.putExtra("workTitle", workTitle);
                intent.putExtra("workCourse", workCourse);

                PendingIntent sender = PendingIntent.getBroadcast(getBaseContext(), flag++, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                if(bTime >= now){
                    am.set(AlarmManager.RTC_WAKEUP, cTime, sender);
                    Toast.makeText(getBaseContext(), getTime2+"에 알람 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), getTime2+ "는 이미 지난 알람입니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}
