package com.jinojino.klashelper.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jinojino.klashelper.DB.DBHelper;
import com.jinojino.klashelper.R;
import com.jinojino.klashelper.receiver.AlarmReceiver;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {

    SharedPreferences data;
    SharedPreferences.Editor editor;
    DBHelper dbHelper;
    String id="";
    String pw="";
    String msg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dbHelper= new DBHelper(getApplicationContext(), "Work.db", null, 1);
        data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //id 받는 칸
        final TextInputEditText idInput = (TextInputEditText) findViewById(R.id.id_input);
        //pw 받는 칸
        final TextInputEditText pwInput = (TextInputEditText) findViewById(R.id.pw_input);

//        ArrayList<String> userList = dbHelper.getUser();
//
//        if(userList.size()>0){
//            id = userList.get(0);
//            pw = userList.get(1);
//            idInput.setText(id);
//            pwInput.setText(pw);
//        }
        if(data.contains("id") || data.contains("pw")){
            id = data.getString("id","");
            pw = data.getString("pw", "");
            idInput.setText(id);
            pwInput.setText(pw);

            msg = "로그인 성공!";

            Intent intent= new Intent(SignInActivity.this, MainActivity.class);
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            startActivity(intent);
        }

        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                ArrayList<String> userList = dbHelper.getUser();
//                if(userList.size()>0){
//                    id = userList.get(0);
//                    pw = userList.get(1);
//                }
//                else{
//
//                }

                if(data.contains("id") || data.contains("pw")){
                    id = data.getString("id","");
                    pw = data.getString("pw", "");
                }

                String id_in = idInput.getText().toString();
                String pw_in = pwInput.getText().toString();

                if(id.compareTo(id_in)==0 && pw.compareTo(pw_in)==0){
                    msg = "로그인 성공!";
                }
                else{
                    dbHelper.deleteAll();
                    dbHelper.setUser(id_in, pw_in);
                    msg = "화면을 내려 과제 목록을 갱신해주세요.";
                }

                editor = data.edit();
                editor.putString("id", idInput.getText().toString());
                editor.putString("pw", pwInput.getText().toString());
                editor.commit();

                //다음 화면으로
                Intent intent= new Intent(SignInActivity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }
}

