package com.jinojino.klashelper.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.jinojino.klashelper.DB.DBHelper;
import com.jinojino.klashelper.R;
import com.jinojino.klashelper.Thread.HttpThread;
import com.jinojino.klashelper.activity.AlarmSettingActivity;
import com.jinojino.klashelper.adapter.ListViewAdapter;
import com.jinojino.klashelper.java.ListViewWork;
import com.jinojino.klashelper.java.Work;


import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorkListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // 제출 on/off
    private static final String TYPE_NAME = "type";
    private static final String FLAG_SUBMIT = "flag";

    DBHelper dbHelper;
    SharedPreferences data;
    SharedPreferences.Editor editor;
    ListView listview ;
    ListViewAdapter adapter;
    SwipeRefreshLayout swipe;
    ArrayList<Work> worklist;


    // TODO: Rename and change types of parameters
    private int type;
    private int flag;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onRefresh() {
        data = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String id = data.getString("id", "default value");
        final String pw = data.getString("pw", "default value");
        dbHelper= new DBHelper(getActivity(), "Work.db", null, 1);

        swipe.setRefreshing(true);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // db 초기화하기
                HttpThread h = new HttpThread(id, pw);
                h.start();
                try {
                    h.join();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                String response = h.getResult();
                try{
                    worklist = new ArrayList<Work>();
                    JSONArray jarray = new JSONArray(response);
                    JSONArray countArray = jarray.getJSONArray(1);

                    for(int j=0; j<countArray.length(); j++){
                        JSONObject workObject = countArray.getJSONObject(j);  // JSONObject 추출
                        int isSubmit = workObject.getInt("isSubmit");
                        String workFile = workObject.getString("workFile");
                        int workType = workObject.getInt("workType");
                        String workCode = workObject.getString("workCode");
                        String createTime = workObject.getString("workCreateTime");
                        String finishTime = workObject.getString("workFinishTime");
                        String workTitle = workObject.getString("workTitle");
                        String workCourse =workObject.getString("workCourse") ;
                        worklist.add(new Work(1, workCode, workCourse, workTitle, createTime, finishTime, isSubmit, 0, 1, workType));
                    }
                    // db에 모든 과제 업데이트
                    dbHelper.updateAll(worklist);
                } catch(JSONException e){
                    e.printStackTrace();
                }
                swipe.setRefreshing(false);
            }
        });
        t.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },20000);

        try {
            t.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(type == 0){
            if(flag == 0){
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview.setAdapter(adapter);
                ArrayList<Work> noSubmitWorklist = dbHelper.getNoSubmitWork();

                for(int i=0; i<noSubmitWorklist.size(); i++){
                    adapter.addItem(noSubmitWorklist.get(i));
                }

            } else{
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview.setAdapter(adapter);

                ArrayList<Work> yesSubmitWorklist = dbHelper.getYesSubmitWork();

                for(int i=0; i<yesSubmitWorklist.size(); i++){
                    adapter.addItem(yesSubmitWorklist.get(i));
                }
            }

        }else{
            if(flag == 0){
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview.setAdapter(adapter);
                ArrayList<Work> noSubmitWorklist = dbHelper.getNoSubmitLecture();

                for(int i=0; i<noSubmitWorklist.size(); i++){
                    adapter.addItem(noSubmitWorklist.get(i));
                }

            } else{
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview.setAdapter(adapter);

                ArrayList<Work> yesSubmitWorklist = dbHelper.getYesSubmitLecture();

                for(int i=0; i<yesSubmitWorklist.size(); i++){
                    adapter.addItem(yesSubmitWorklist.get(i));
                }
            }
        }
    }

    public WorkListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WorkListFragment newInstance(int type, int flag) {
        WorkListFragment fragment = new WorkListFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_NAME, type);
        args.putInt(FLAG_SUBMIT, flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_work_list, container, false);
        Bundle args = getArguments();
        type = args.getInt(TYPE_NAME);
        flag = args.getInt(FLAG_SUBMIT);

        swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipe_layout);
        swipe.setOnRefreshListener(this);

        data = PreferenceManager.getDefaultSharedPreferences(getContext());
        String id = data.getString("id", "default value");
        String pw = data.getString("pw", "default value");

        dbHelper= new DBHelper(getActivity(), "Work.db", null, 1);
        if(type == 0){
            if(flag == 0){
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);
                ArrayList<Work> noSubmitWorklist = dbHelper.getNoSubmitWork();

                for(int i=0; i<noSubmitWorklist.size(); i++){
                    adapter.addItem(noSubmitWorklist.get(i));
                }

            } else{
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);

                ArrayList<Work> yesSubmitWorklist = dbHelper.getYesSubmitWork();

                for(int i=0; i<yesSubmitWorklist.size(); i++){
                    adapter.addItem(yesSubmitWorklist.get(i));
                }
            }
        }else{
            if(flag == 0){
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);
                ArrayList<Work> noSubmitWorklist = dbHelper.getNoSubmitLecture();

                for(int i=0; i<noSubmitWorklist.size(); i++){
                    adapter.addItem(noSubmitWorklist.get(i));
                }

            } else{
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);

                ArrayList<Work> yesSubmitWorklist = dbHelper.getYesSubmitLecture();

                for(int i=0; i<yesSubmitWorklist.size(); i++){
                    adapter.addItem(yesSubmitWorklist.get(i));
                }
            }

        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void refreshList(View view){
        if(type == 0){
            if(flag == 0){
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);
                ArrayList<Work> noSubmitWorklist = dbHelper.getNoSubmitWork();

                for(int i=0; i<noSubmitWorklist.size(); i++){
                    adapter.addItem(noSubmitWorklist.get(i));
                }

            } else{
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);

                ArrayList<Work> yesSubmitWorklist = dbHelper.getYesSubmitWork();

                for(int i=0; i<yesSubmitWorklist.size(); i++){
                    adapter.addItem(yesSubmitWorklist.get(i));
                }
            }
        }else{
            if(flag == 0){
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);
                ArrayList<Work> noSubmitWorklist = dbHelper.getNoSubmitLecture();

                for(int i=0; i<noSubmitWorklist.size(); i++){
                    adapter.addItem(noSubmitWorklist.get(i));
                }

            } else{
                // Adapter 생성
                adapter = new ListViewAdapter() ;

                // 리스트뷰 참조 및 Adapter달기
                listview = (ListView) view.findViewById(R.id.list_work);
                listview.setAdapter(adapter);

                ArrayList<Work> yesSubmitWorklist = dbHelper.getYesSubmitLecture();

                for(int i=0; i<yesSubmitWorklist.size(); i++){
                    adapter.addItem(yesSubmitWorklist.get(i));
                }
            }

        }
    }
}
