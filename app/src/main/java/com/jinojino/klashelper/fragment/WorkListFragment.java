package com.jinojino.klashelper.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.jinojino.klashelper.DB.DBHelper;
import com.jinojino.klashelper.R;
import com.jinojino.klashelper.activity.AlarmSettingActivity;
import com.jinojino.klashelper.adapter.ListViewAdapter;
import com.jinojino.klashelper.java.ListViewWork;
import com.jinojino.klashelper.java.Work;


import java.util.ArrayList;

public class WorkListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    DBHelper dbHelper;


    // TODO: Rename and change types of parameters
    private int mParam1;

    private OnFragmentInteractionListener mListener;

    public WorkListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WorkListFragment newInstance(int param1) {
        WorkListFragment fragment = new WorkListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
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
        mParam1 = args.getInt(ARG_PARAM1);

        dbHelper= new DBHelper(getActivity(), "Work.db", null, 1);
        ListView listview ;
        final ListViewAdapter adapter;



        if(mParam1 == 0){
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

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), AlarmSettingActivity.class);
//                String workCode = ((ListViewWork)adapter.getItem(position)).getWorkCode();
//                // putExtra(key, value)
//                intent.putExtra("workCode", workCode);
//                startActivity(intent);
//            }
//        });

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
}
