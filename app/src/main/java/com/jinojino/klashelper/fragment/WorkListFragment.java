package com.jinojino.klashelper.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jinojino.klashelper.R;
import com.jinojino.klashelper.adapter.ListViewAdapter;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

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

        TextView t = (TextView)view.findViewById(R.id.text);

        if(mParam1 == 0){
            ListView listview ;
            ListViewAdapter adapter;

            // Adapter 생성
            adapter = new ListViewAdapter() ;

            // 리스트뷰 참조 및 Adapter달기
            listview = (ListView) view.findViewById(R.id.list_work);
            listview.setAdapter(adapter);

            // 첫 번째 아이템 추가.
            adapter.addItem("자료구조", "니알아서해과제");
            // 두 번째 아이템 추가.
            adapter.addItem(
                    "데이터베이스", "기간안에절대못해과제") ;
            // 세 번째 아이템 추가.
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;

        } else{
            ListView listview ;
            ListViewAdapter adapter;

            // Adapter 생성
            adapter = new ListViewAdapter() ;

            // 리스트뷰 참조 및 Adapter달기
            listview = (ListView) view.findViewById(R.id.list_work);
            listview.setAdapter(adapter);

            // 첫 번째 아이템 추가.
            adapter.addItem("자료구조", "니알아서해과제");
            // 두 번째 아이템 추가.
            adapter.addItem(
                    "데이터베이스", "기간안에절대못해과제") ;
            // 세 번째 아이템 추가.
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
            adapter.addItem(
                    "운영체제", "베끼면빵점과제") ;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
