package com.example.apoorva.timer;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView title;
    Button timer;
    Clock[] times;
    int timeCtr;

    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;
    TextView time5;
    TextView time6;
    TextView time7;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView title = (TextView)view.findViewById(R.id.listTitle);
            title.setTextSize(20);
            timer = (Button)view.findViewById(R.id.seeTimer);
            timer.setVisibility(view.GONE);
        }
        times = new Clock[7];
        timeCtr = 0;

        time1 = (TextView)view.findViewById(R.id.time1);
        time2 = (TextView)view.findViewById(R.id.time2);
        time3 = (TextView)view.findViewById(R.id.time3);
        time4 = (TextView)view.findViewById(R.id.time4);
        time5 = (TextView)view.findViewById(R.id.time5);
        time6 = (TextView)view.findViewById(R.id.time6);
        time7 = (TextView)view.findViewById(R.id.time7);


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

    public void setTimes(Timestamps listOfTimes){
        String [] allTimes = listOfTimes.getTimeStamps();
        if(listOfTimes.getTimeCtr()==0){
            time1.setText("00:00:00");
            time2.setText("00:00:00");
            time3.setText("00:00:00");
            time4.setText("00:00:00");
            time5.setText("00:00:00");
            time6.setText("00:00:00");
            time7.setText("00:00:00");
        }
        else {
            if (listOfTimes.getTimeCtr() > 0 ) time1.setText(allTimes[0]);
            if (listOfTimes.getTimeCtr() > 1 ) time2.setText(allTimes[1]);
            if (listOfTimes.getTimeCtr() > 2 ) time3.setText(allTimes[2]);
            if (listOfTimes.getTimeCtr() > 3 ) time4.setText(allTimes[3]);
            if (listOfTimes.getTimeCtr() > 4 ) time5.setText(allTimes[4]);
            if (listOfTimes.getTimeCtr() > 5 ) time6.setText(allTimes[5]);
            if (listOfTimes.getTimeCtr() > 6 ) time7.setText(allTimes[6]);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}