package com.jinojino.klashelper.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jinojino.klashelper.DB.DBHelper;
import com.jinojino.klashelper.R;
import com.jinojino.klashelper.adapter.AlarmAdapter;
import com.jinojino.klashelper.adapter.ListViewAdapter;
import com.jinojino.klashelper.java.Alarm;
import com.jinojino.klashelper.java.ListViewAlarm;
import com.jinojino.klashelper.java.ListViewWork;
import com.jinojino.klashelper.receiver.AlarmReceiver;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlarmSettingActivity extends AppCompatActivity {

    String workCourse;
    String workTitle;
    String workCode;
    DatePicker datePicker;
    TimePicker timePicker;
    TextInputEditText editText;
    ListView alarmListView;
    ArrayList<Alarm> alarmList;

    DBHelper dbHelper;

    @Override
    protected void onResume() {
        super.onResume();

        // 알람 추가 후 리스트 초기화
        dbHelper= new DBHelper(this, "Work.db", null, 1);
        final AlarmAdapter adapter = new AlarmAdapter();
        alarmListView.setAdapter(adapter);
        alarmList = dbHelper.getAlarm(workCode);
        for(int i=0; i<alarmList.size(); i++){
            adapter.addItem(alarmList.get(i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        // db 초기화
        dbHelper= new DBHelper(this, "Work.db", null, 1);

        // 알람에 필요한 정보 추가
        Intent intent = getIntent();
        workCourse = intent.getExtras().getString("workCourse");
        workTitle = intent.getExtras().getString("workTitle");
        workCode = intent.getExtras().getString("workCode");

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.time_picker);
        Button button = (Button)findViewById(R.id.setButton);
        editText = (TextInputEditText)findViewById(R.id.editText);
        alarmListView = (ListView)findViewById(R.id.list_alarm);

        final AlarmAdapter adapter = new AlarmAdapter();

        alarmListView.setAdapter(adapter);
        alarmList = dbHelper.getAlarm(workCode);
        for(int i=0; i<alarmList.size(); i++){
            adapter.addItem(alarmList.get(i));
        }

        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(AlarmSettingActivity.this);
                final View parentView = (View)view.getParent();
                alert_confirm.setMessage("알람을 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 알람 삭제
                                int temp = alarmList.get(position).getAlarmId();
                                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                                PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), temp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager am = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                                am.cancel(sender);
                                sender.cancel();

                                // db 삭제
                                dbHelper.deleteAlarmById(temp);
                                onResume();
                                return;
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

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
                intent.putExtra("alarmTitle", editText.getText().toString());

                if(bTime >= now){
                    // DB 등록
                    Alarm tempAlarm = new Alarm(0, editText.getText().toString(),workCode, getTime2, workCourse, workTitle);
                    int temp = dbHelper.addAlarm(tempAlarm);

                    //알람 등록
                    PendingIntent sender = PendingIntent.getBroadcast(getBaseContext(), temp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    am.set(AlarmManager.RTC_WAKEUP, cTime, sender);
                    Toast.makeText(getBaseContext(), getTime2+"에 알람 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    onResume();
                }else{
                    Toast.makeText(getBaseContext(), getTime2+ "는 이미 지난 알람입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
