package com.jinojino.klashelper.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Work (id INTEGER PRIMARY KEY AUTOINCREMENT, workCode TEXT UNIQUE, workCourse TEXT, workTitle TEXT, workCreateTime TEXT, workFinishTime TEXT, submitOrNot INTEGER, workAlarm INTEGER);");
        db.execSQL("Insert into Work Values(null, 'sef', '자료구조', '2018-02-03 15:00', '2018-02-04 15:00', 0, 1);");
        db.execSQL("Insert into Work Values(null, 'fefef', '데이터베이스', '2018-02-01 15:00', '2018-02-02 15:00', 0, 0);");
    }

//    public ArrayList<Entry> getAllNoise() {
//        // 읽기가 가능하게 DB 열기
//        SQLiteDatabase db = getReadableDatabase();
//        ArrayList<Entry> entries = new ArrayList<>();
//
//        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
//        Cursor cursor = db.rawQuery("SELECT * FROM Room ", null);
//        while (cursor.moveToNext()) {
//            entries.add(new Entry(cursor.getInt(3), cursor.getInt(2)));
//        }
//
//        return entries;
//    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("DELETE FROM Work");
    }

    public void insert(String create_at, String roomName, int noise) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO Work VALUES(null, '" + roomName + "', " + noise + ", '" + create_at + "');");
    }

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

