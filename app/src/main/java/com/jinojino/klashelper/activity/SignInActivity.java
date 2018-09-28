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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dbHelper= new DBHelper(getApplicationContext(), "Work.db", null, 1);

        //id 받는 칸
        final TextInputEditText idInput = (TextInputEditText) findViewById(R.id.id_input);
        //pw 받는 칸
        final TextInputEditText pwInput = (TextInputEditText) findViewById(R.id.pw_input);

        ArrayList<String> userList = dbHelper.getUser();

        if(userList.size()>0){
            id = userList.get(0);
            pw = userList.get(1);
            idInput.setText(id);
            pwInput.setText(pw);
        }

        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ArrayList<String> userList = dbHelper.getUser();
                if(userList.size()>0){
                    id = userList.get(0);
                    pw = userList.get(1);
                }
                else{
                    Log.d("로그", "노존재노존재");
                }
                String id_in = idInput.getText().toString();
                String pw_in = pwInput.getText().toString();

                if(id.compareTo(id_in)==0 && pw.compareTo(pw_in)==0){

                }
                else{
                    dbHelper.deleteAll();
                    dbHelper.setUser(id_in, pw_in);
                }

                data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                editor = data.edit();
                editor.putString("id", idInput.getText().toString());
                editor.putString("pw", pwInput.getText().toString());
                editor.commit();

                //다음 화면으로
                Intent intent= new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
