package com.example.stpntimer.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;


public class AlarmFrag extends Fragment {

    private TimePicker timePicker;
    private Button button;
    private ListView alarmList;
    private ArrayAdapter arrayAdapter;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_alarm_frag,null,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        v=getView();

        timePicker=(TimePicker)v.findViewById(R.id.timePicker);
        alarmList=(ListView)v.findViewById(R.id.alarm_list);

        button=(Button)v.findViewById(R.id.add_button);
        button.setOnClickListener(add_alarm);

        arrayAdapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.listview_custom);
        alarmList.setAdapter(arrayAdapter);
    }

    View.OnClickListener add_alarm=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int hour=timePicker.getCurrentHour();
            int min=timePicker.getCurrentMinute();
            String am_pm;
            if (hour>12) {
                am_pm="pm";
                hour-=12;
            } else {
                am_pm="am";
            }
            String t=addZero(Integer.toString(hour))+":"+addZero(Integer.toString(min))+" "+am_pm;
            arrayAdapter.add(t);
        }
    };

    private String addZero(String input){
        if (input.isEmpty()) {
            input="00"+input;
        } else if (input.length()<2) {
            input="0"+input;
        }
        return input;
    }
}
