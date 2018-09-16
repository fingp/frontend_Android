package com.jinojino.klashelper.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


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
        db.execSQL("CREATE TABLE Work (id INTEGER PRIMARY KEY AUTOINCREMENT, workCode TEXT UNIQUE, workCourse TEXT, workTitle TEXT, workCreateTime TEXT, workFinishTime TEXT, submitFlag INTEGER, workAlarm INTEGER, isAlive INTEGER);");
        //db.execSQL("CREATE TABLE Work (id INTEGER PRIMARY KEY AUTOINCREMENT, lectureCode TEXT UNIQUE, lectureCourse TEXT, lectureTitle TEXT, lectureStudyTime TEXT, lectureTotalTime TEXT, submitFlag INTEGER, lectureAlarm INTEGER, isAlive Boolean);");
        db.execSQL("Insert into Work Values(null, 'sef1', '자료구조', '과제1', '2018-02-03 15:00', '2018-09-17 13:47', 0, 0, 1);");
        db.execSQL("Insert into Work Values(null, 'sef2', '자료구조', '과제2', '2018-02-03 15:00', '2018-09-17 13:48', 0, 0, 1);");
        db.execSQL("Insert into Work Values(null, 'sef3', '자료구조', '과제3', '2018-02-03 15:00', '2018-10-06 15:00', 0, 0, 1);");
        db.execSQL("Insert into Work Values(null, 'sef4', '자료구조', '과제4', '2018-02-03 15:00', '2018-10-07 15:00', 0, 0, 1);");
        db.execSQL("Insert into Work Values(null, 'fefef1', '데이터베이스', '과제1', '2018-02-01 15:00', '2018-10-08 15:00', 1, 0, 1);");
        db.execSQL("Insert into Work Values(null, 'fefef2', '데이터베이스', '과제2', '2018-02-01 15:00', '2018-10-09 15:00', 1, 0, 1);");
        db.execSQL("Insert into Work Values(null, 'fefef3', '데이터베이스', '과제3', '2018-02-01 15:00', '2018-10-10 15:00', 0, 0, 1);");
        db.execSQL("Insert into Work Values(null, 'fefef4', '데이터베이스', '과제4', '2018-02-01 15:00', '2018-09-17 13:49', 0, 0, 1);");
    }
    // 서버에서 해당 코드 과제 업데이트
    public void update(Work work) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE Work SET workFinishTime=" + work.getDateFinish() + ", submitFlag=" +work.getSubmitFlag() + ", isAlive= " + work.getAlive() + " WHERE workCode =" + work.getCodeWork() + ";" );
        db.close();
    }
    public void updateAlarm(String code, int alarm) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE Work SET workAlarm =  " + alarm + " WHERE workCode = '" + code + "';");
        db.close();
    }

    // 제출 안한 과제 가져오기
    public ArrayList<Work> getNoSubmitWork(){
        ArrayList<Work> workList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Work WHERE submitFlag = 0 ORDER BY datetime(workFinishTime) ASC;", null);
        while (cursor.moveToNext()) {
            workList.add(new Work(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8)));
        }

        return workList;
    }

    // 제출 안한 과제 가져오기
    public ArrayList<Work> getYesSubmitWork(){

        ArrayList<Work> workList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Work WHERE submitFlag = 1 ORDER BY datetime(workFinishTime) ASC;", null);
        while (cursor.moveToNext()) {
            workList.add(new Work(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8)));
        }

        return workList;
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public void delete() {
//        SQLiteDatabase db = getWritableDatabase();
//        // DB에 입력한 값으로 행 추가
//        db.execSQL("DELETE FROM Work");
//    }
//
//    public void insert(String create_at, String roomName, int noise) {
//        // 읽고 쓰기가 가능하게 DB 열기
//        SQLiteDatabase db = getWritableDatabase();
//        // DB에 입력한 값으로 행 추가
//        db.execSQL("INSERT INTO Work VALUES(null, '" + roomName + "', " + noise + ", '" + create_at + "');");
//    }
//    public void update(String item, int price) {
//        SQLiteDatabase db = getWritableDatabase();
//        // 입력한 항목과 일치하는 행의 가격 정보 수정
//        db.execSQL("UPDATE MONEYBOOK SET price=" + price + " WHERE item='" + item + "';");
//        db.close();
//    }

//    public void delete(String item) {
//        SQLiteDatabase db = getWritableDatabase();
//        // 입력한 항목과 일치하는 행 삭제
//        db.execSQL("DELETE FROM MONEYBOOK WHERE item='" + item + "';");
//        db.close();
//    }
}

