package com.jinojino.klashelper.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.ArrayRes;
import android.util.Log;
import android.widget.Toast;


import com.jinojino.klashelper.java.Alarm;
import com.jinojino.klashelper.java.Work;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    Context c;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        c = context;
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Work (id INTEGER PRIMARY KEY AUTOINCREMENT, workCode TEXT UNIQUE, workCourse TEXT, workTitle TEXT, workCreateTime TEXT, workFinishTime TEXT, submitFlag INTEGER, workAlarm INTEGER, isAlive INTEGER, workType INTEGER);");
        db.execSQL("CREATE TABLE Alarm (id INTEGER PRIMARY KEY AUTOINCREMENT, alarmID INTEGER, workCode TEXT, alarmTime TEXT, alarmTitle TEXT);");
        db.execSQL("CREATE TABLE Count (id INTEGER PRIMARY KEY AUTOINCREMENT, alarmID INTEGER, broadID INTEGER);");
        db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, id_s TEXT, pw TEXT);");
    }
    public void setUser(String id, String pw){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM USER;");
        db.execSQL("INSERT INTO USER VALUES(null, '" + id+ "', '" +  pw + "' );");

    }
    public ArrayList<String> getUser(){
        ArrayList<String > stringlist =new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER;", null);
        while (cursor.moveToNext()) {
            stringlist.add( cursor.getString(1));
            stringlist.add(cursor.getString(2));
        }
        return stringlist;
    }
    public void updateAll(ArrayList<Work> worklist){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteDatabase db1 = getWritableDatabase();


        db.execSQL("UPDATE Work SET  isAlive=0;");


        for(int i=0; i<worklist.size(); i++){
            Work temp = worklist.get(i);
            db1.execSQL(" INSERT OR REPLACE INTO Work VALUES (null, '" +temp.getCodeWork() + "', '" + temp.getNameCoruse() + "', '" + temp.getNameWork() + "', '"+ temp.getDateStart() + "', '"+ temp.getDateFinish() + "', "+ temp.getSubmitFlag() + ", "+ temp.getAlarm() + ", "+ temp.getAlive() + ", "+ temp.getWorkType() + ");");

        }
        db.close();
    }
    // 서버에서 해당 코드 과제 업데이트
    public void update(Work work) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE Work SET workFinishTime=" + work.getDateFinish() + ", submitFlag=" +work.getSubmitFlag() + ", isAlive= " + work.getAlive() + " WHERE workCode =" + work.getCodeWork() + ";" );
        db.close();
    }

    // 스위치 On/Off
    public void updateAlarm(String code, int alarm) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE Work SET workAlarm =  " + alarm + " WHERE workCode = '" + code + "';");
        db.close();
    }

    // 미제출 과제 가져오기
    public ArrayList<Work> getNoSubmitWork(){
        ArrayList<Work> workList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Work WHERE submitFlag = 0 AND isAlive = 1 AND workType=0 ORDER BY datetime(workFinishTime) ASC;", null);
        while (cursor.moveToNext()) {
            workList.add(new Work(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9)));
        }

        return workList;
    }

    // 제출 과제 가져오기
    public ArrayList<Work> getYesSubmitWork(){

        ArrayList<Work> workList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Work WHERE submitFlag = 1 AND isAlive = 1 AND workType=0 ORDER BY datetime(workFinishTime) ASC;", null);
        while (cursor.moveToNext()) {
            workList.add(new Work(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8), cursor.getInt(9)));
        }

        return workList;
    }

    // 해당 과제 알람 모두 가져오기
    public ArrayList<Alarm> getAlarm(String workCode){
        ArrayList<Alarm> alarmList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT Alarm.alarmID, Alarm.alarmTitle, Alarm.workCode, Alarm.alarmTime, Work.workCourse, Work.workTitle  FROM Alarm INNER JOIN Work ON Alarm.workCode = Work.workCode WHERE Alarm.workCode = '" + workCode + "' ORDER BY datetime(alarmTime) ASC;", null);
        while (cursor.moveToNext()) {
            alarmList.add(new Alarm(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
        }
        return alarmList;
    }

    // 해당 과제 모든 알람 삭제 위해 모든 id 가져오기
    public ArrayList<Integer> getAlarmId(String workCode){
        ArrayList<Integer> idList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT alarmID FROM Alarm WHERE workCode = '" + workCode + "' ORDER BY datetime(alarmTime) ASC;", null);
        while (cursor.moveToNext()) {
            idList.add(new Integer(cursor.getInt(0)));
        }
        return idList;
    }

    // 해당 과제 알람 모두 삭제
    public void deleteAlarmByWorkcode(String workCode){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("DELETE FROM Alarm WHERE workCode = '" +workCode + "';");

    }

    // 알람 단일 삭제
    public void deleteAlarmById(int alarmID){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("DELETE FROM Alarm WHERE alarmID = '" +alarmID + "';");

    }

    // 알람 생성
    public int addAlarm(Alarm alarm){
        int temp = 0;
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT max(id) FROM Alarm;",null);
        while (cursor.moveToNext()) {
            temp =  cursor.getInt(0);
        }

        temp++;

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Insert into Alarm Values(null, " + temp +", '" + alarm.getWorkCode() + "', '"  + alarm.getAlarmTime() + "', '"+ alarm.getAlarmTitle() +"');");

        return temp;
    }

    public int addNotify(int trash){
        int temp = 0;
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT max(id) FROM Count;",null);
        while (cursor.moveToNext()) {
            temp =  cursor.getInt(0);
        }

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Insert into Count Values(null, 0, 0);");

        return temp++;
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM Work ;");
        db.close();

    }


    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

