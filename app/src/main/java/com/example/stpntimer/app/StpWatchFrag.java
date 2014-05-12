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


public class StpWatchFrag extends Fragment {

    Chronometer crnmeter;
    boolean flag=false;
    long timestop=0;
    ListView listView;
    ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        crnmeter=(Chronometer)getView().findViewById(R.id.chronometer);
        listView=(ListView)getView().findViewById(R.id.listView);
        Button button;

        button=(Button)getView().findViewById(R.id.startbtn);
        button.setOnClickListener(Start);
        button=(Button)getView().findViewById(R.id.stopbtn);
        button.setVisibility(View.GONE);
        button.setOnClickListener(Stop);
        button=(Button)getView().findViewById(R.id.resetbtn);
        button.setVisibility(View.GONE);
        button.setOnClickListener(Reset);
        button=(Button)getView().findViewById(R.id.recordbtn);
        button.setVisibility(View.GONE);
        button.setOnClickListener(Record);

        arrayAdapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.listview_custom);
        listView.setAdapter(arrayAdapter);

        return inflater.inflate(R.layout.stpwatchfrag,container,false);
    }

    View.OnClickListener Start=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            crnmeter.setBase(SystemClock.elapsedRealtime()+timestop);
            crnmeter.start();
            flag=true;
            Button btn;
            btn=(Button)getView().findViewById(R.id.startbtn);
            btn.setVisibility(View.GONE);
            btn=(Button)getView().findViewById(R.id.stopbtn);
            btn.setVisibility(View.VISIBLE);
            btn=(Button)getView().findViewById(R.id.resetbtn);
            btn.setVisibility(View.VISIBLE);
            btn=(Button)getView().findViewById(R.id.recordbtn);
            btn.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener Stop=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn=(Button)getView().findViewById(R.id.stopbtn);
            if(flag){
                timestop=crnmeter.getBase()-SystemClock.elapsedRealtime();
                crnmeter.stop();
                flag=false;
                btn.setText("RESTART");
                btn=(Button)getView().findViewById(R.id.recordbtn);
                btn.setVisibility(View.GONE);
            }else{
                crnmeter.setBase(SystemClock.elapsedRealtime()+timestop);
                crnmeter.start();
                flag=true;
                btn.setText("STOP");
                btn=(Button)getView().findViewById(R.id.recordbtn);
                btn.setVisibility(View.GONE);
            }
        }
    };

    View.OnClickListener Reset=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*
            crnmeter.stop();
            crnmeter.setBase(SystemClock.elapsedRealtime());
            timestop=0;
            arrayAdapter.clear();
            flag=false;
            Button btn;
            btn=(Button)getView().findViewById(R.id.startbtn);
            btn.setVisibility(View.VISIBLE);
            btn=(Button)getView().findViewById(R.id.stopbtn);
            btn.setText("STOP");
            btn.setVisibility(View.GONE);
            btn=(Button)getView().findViewById(R.id.resetbtn);
            btn.setVisibility(View.GONE);
            btn=(Button)getView().findViewById(R.id.recordbtn);
            btn.setVisibility(View.GONE);
            */
        }
    };

    View.OnClickListener Record=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            long rectime=(SystemClock.elapsedRealtime()-crnmeter.getBase());
            String recmin=Long.toString(rectime/60000);
            if(recmin.length()<2){
                recmin="0"+recmin;
            }
            String recsec=Double.toString((rectime%60000)/1000.0);
            if(recsec.charAt(1)=='.'){
                recsec="0"+recsec;
            }
            String srectime=recmin+":"+recsec;
            arrayAdapter.add(srectime);
            arrayAdapter.notifyDataSetChanged();
        }
    };
}
