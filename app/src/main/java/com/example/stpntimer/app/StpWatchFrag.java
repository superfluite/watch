package com.example.stpntimer.app;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;


public class StpWatchFrag extends Fragment{

    private Chronometer chronometer;
    private boolean flag=false;
    private long timestop=0;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stpwatchfrag,null,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chronometer=(Chronometer)getView().findViewById(R.id.chronometer);
        listView=(ListView)getView().findViewById(R.id.listView);

        button=(Button)getView().findViewById(R.id.startbtn);
        button.setOnClickListener(start);
        button=(Button)getView().findViewById(R.id.stopbtn);
        button.setOnClickListener(stop);
        button=(Button)getView().findViewById(R.id.resetbtn);
        button.setOnClickListener(reset);
        button=(Button)getView().findViewById(R.id.recordbtn);
        button.setOnClickListener(record);

        changeAllButtonVisibility();
        changeViewVisibility(R.id.startbtn);

        arrayAdapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.listview_custom);
        listView.setAdapter(arrayAdapter);
    }

    View.OnClickListener start=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            chronometer.setBase(SystemClock.elapsedRealtime()+timestop);
            chronometer.start();
            flag=true;
            changeAllButtonVisibility();
        }
    };

    View.OnClickListener stop=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            button=(Button)getView().findViewById(R.id.stopbtn);
            if (flag) {
                timestop=chronometer.getBase()-SystemClock.elapsedRealtime();
                chronometer.stop();
                flag=false;
                button.setText("RESTART");
            } else {
                chronometer.setBase(SystemClock.elapsedRealtime() + timestop);
                chronometer.start();
                flag=true;
                button.setText("STOP");
            }
        }
    };

    View.OnClickListener reset=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            timestop=0;
            arrayAdapter.clear();
            flag=false;
            changeAllButtonVisibility();
            button=(Button)getView().findViewById(R.id.stopbtn);
            button.setText("STOP");
        }
    };

    View.OnClickListener record=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (flag) {
                long rectime = (SystemClock.elapsedRealtime() - chronometer.getBase());
                String recmin = Long.toString(rectime / (60 * 1000));
                if (recmin.length() < 2) {
                    recmin = "0" + recmin;
                }
                String recsec = Double.toString((rectime % (60 * 1000)) / 1000.0);
                if (recsec.charAt(1) == '.') {
                    recsec = "0" + recsec;
                }
                String srectime = recmin + ":" + recsec;
                arrayAdapter.add(srectime);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private void changeViewVisibility(int id){
        View Target=(View)getView().findViewById(id);
        if (Target.getVisibility()==View.VISIBLE) {
            Target.setVisibility(View.GONE);
        } else {
            Target.setVisibility(View.VISIBLE);
        }
    }

    private void changeAllButtonVisibility(){
        changeViewVisibility(R.id.startbtn);
        changeViewVisibility(R.id.stopbtn);
        changeViewVisibility(R.id.resetbtn);
        changeViewVisibility(R.id.recordbtn);
    }
}
