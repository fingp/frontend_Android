package com.jinojino.klashelper.adapter;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.util.Log;
import android.widget.BaseAdapter;

import com.jinojino.klashelper.DB.DBHelper;
import com.jinojino.klashelper.R;
import com.jinojino.klashelper.activity.AlarmSettingActivity;
import com.jinojino.klashelper.java.ListViewWork;
import com.jinojino.klashelper.java.Work;
import com.jinojino.klashelper.receiver.AlarmReceiver;
import com.jinojino.klashelper.service.AlarmService;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewWork> listViewWorkList = new ArrayList<ListViewWork>() ;
    DBHelper dbHelper;

    private static final String TAG = "TestAlarmManagerActivity";
    private static final String INTENT_ACTION = "arabiannight.tistory.com.alarmmanager";
    static  int flag=0;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewWorkList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        dbHelper= new DBHelper(context, "Work.db", null, 1);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_work, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final TextView workCourseView = (TextView) convertView.findViewById(R.id.textView1) ;
        final TextView workTitleView = (TextView) convertView.findViewById(R.id.textView2) ;
        final TextView workDateView = (TextView) convertView.findViewById(R.id.textView3) ;
        final Switch alaramSwitch = (Switch) convertView.findViewById(R.id.switch1);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewWork listViewWork = listViewWorkList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        workCourseView.setText(listViewWork.getWorkCourse());
        workTitleView.setText(listViewWork.getWorkTitle());
        workDateView.setText(listViewWork.getWorkDate());
        alaramSwitch.setChecked(listViewWork.getWorkAlarm() <= 0 ? false : true);

        // 날짜 아이템 선택
        workDateView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                View parentView = (View)view.getParent();
                String pos = (String) parentView.getTag();
                String code = listViewWorkList.get(position).getWorkCode();

                Intent intent = new Intent(context, AlarmSettingActivity.class);
                String workCourse = workCourseView.getText().toString();
                String workTitle = workTitleView.getText().toString();
                String workCode = workDateView.getText().toString();
                // putExtra(key, value)
                intent.putExtra("workCode", workCode);
                intent.putExtra("workTitle", workTitle);
                intent.putExtra("workCourse", workCourse);
                context.startActivity(intent);
            }
        });

        // 알람 스위치 설정
        alaramSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                View parentView = (View)view.getParent();
                String pos = (String) parentView.getTag();
                String workCode = listViewWorkList.get(position).getWorkCode();
                String workTitle = listViewWorkList.get(position).getWorkTitle();
                String workCourse = listViewWorkList.get(position).getWorkCourse();
                String dateFinish = listViewWorkList.get(position).getWorkDate();

                if(alaramSwitch.isChecked()){
                    // DB 업데이트 하기
                    dbHelper.updateAlarm(workCode, alaramSwitch.isChecked()? 1 : 0);

                    // 알람 등록하기
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Calendar calendar = Calendar.getInstance();

                    Log.d("해당날짜", dateFinish);
                    try{
                        Date raw = sdf.parse(dateFinish);
                        calendar.setTime(raw);
                    }catch (Exception e){
                        Log.d("오류", "오류발생");
                    }

                    long now = System.currentTimeMillis();
                    long bTime = calendar.getTimeInMillis();
                    long interval = 1000 * 60 * 60  * 24;
                    bTime = bTime-interval;

                    Date date = new Date(now);
                    Date date1 = new Date(bTime);

                    String getTime = sdf.format(date);
                    String getTime2 = sdf.format(date1);

                    AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(context, AlarmReceiver.class);
                    intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    intent.putExtra("workCode", workCode);
                    intent.putExtra("workTitle", workTitle);
                    intent.putExtra("workCourse", workCourse);

                    PendingIntent sender = PendingIntent.getBroadcast(context, flag++, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    if(bTime >= now){
                        am.set(AlarmManager.RTC_WAKEUP, bTime, sender);
                        Toast.makeText(context, getTime2+"에 알람 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, getTime2+ "는 이미 지난 알람입니다.", Toast.LENGTH_SHORT).show();
                    }
//                    AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//                    Intent intent = new Intent(context, AlarmReceiver.class);
//                    intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//
//                    PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//
//                    Calendar calendar = Calendar.getInstance();
//                    //알람시간 calendar에 set해주기
//
//                    calendar.set(Calendar.HOUR_OF_DAY, 1);
//                    calendar.set(Calendar.MINUTE, 40);
//                    calendar.set(Calendar.SECOND, 0);
//
//                    long aTime = System.currentTimeMillis();
//                    long bTime = calendar.getTimeInMillis();
//
//                    //하루의 시간을 나타냄
//                    long interval = 1000 * 60 * 60  * 24;
//
//                    //만일 내가 설정한 시간이 현재 시간보다 작다면 알람이 바로 울려버리기 때문에 이미 시간이 지난 알람은 다음날 울려야 한다.
//                    while(aTime>bTime){
//                        bTime += interval;
//                    }
//
//
//                    //알람 예약
//                    am.set(AlarmManager.RTC_WAKEUP, bTime, sender);
//
//
//                    Toast.makeText(context, "알람 등록 완료", Toast.LENGTH_SHORT).show();
                }else{
                    // DB 업데이트 하기
                    dbHelper.updateAlarm(workCode, alaramSwitch.isChecked()? 1 : 0);

                    // 알람 해제하기

                    Toast.makeText(context, "알람 해제 완료", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewWorkList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Work work) {
        ListViewWork item = new ListViewWork();

        item.setWorkCourse(work.getNameCoruse());
        item.setWorkTitle(work.getNameWork());
        item.setWorkDate(work.getDateFinish());
        item.setWorkAlarm(work.getAlarm());
        item.setWorkCode(work.getCodeWork());

        listViewWorkList.add(item);
    }
}

