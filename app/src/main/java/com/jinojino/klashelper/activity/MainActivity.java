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
import com.jinojino.klashelper.fragment.LectureFragment;
import com.jinojino.klashelper.fragment.WorkFragment;
import com.jinojino.klashelper.fragment.WorkListFragment;


public class MainActivity extends AppCompatActivity implements WorkFragment.OnFragmentInteractionListener, BoardFragment.OnFragmentInteractionListener, LectureFragment.OnFragmentInteractionListener, WorkListFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.work:
                    replaceFragment(new WorkFragment());
                    return true;
                case R.id.lecture:
                    replaceFragment(new LectureFragment());
                    return true;
                case R.id.dashboard:
                    replaceFragment(new BoardFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
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
