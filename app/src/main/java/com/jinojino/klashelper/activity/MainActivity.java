package com.jinojino.klashelper.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.jinojino.klashelper.R;
import com.jinojino.klashelper.fragment.BoardFragment;
import com.jinojino.klashelper.fragment.ClovaFragment;
import com.jinojino.klashelper.fragment.WorkFragment;


public class MainActivity extends AppCompatActivity implements WorkFragment.OnFragmentInteractionListener, BoardFragment.OnFragmentInteractionListener, ClovaFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("과제");
                    replaceFragment(new WorkFragment());
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("게시판");
                    replaceFragment(new BoardFragment());
                    return true;
                case R.id.navigation_notifications:
                    setTitle("클로바 연동");
                    replaceFragment(new ClovaFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_fragment, new WorkFragment()).commit();
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
