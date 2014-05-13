package com.example.stpntimer.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;


public class TimerFrag extends Fragment{

    private EditText input_hour;
    private EditText input_min;
    private EditText input_sec;
    private TextView timer;
    private Button button;
    private CountDownTimer countDownTimer=null;
    private Boolean flag=false;
    private String TimeSave;
    protected InputFilter NumFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timerfrag,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button=(Button)getView().findViewById(R.id.start_button);
        button.setOnClickListener(start);
        button=(Button)getView().findViewById(R.id.stop_button);
        button.setOnClickListener(stop);
        button=(Button)getView().findViewById(R.id.reset_button);
        button.setOnClickListener(reset);

        changeViewVisibility(R.id.stop_button);
        changeViewVisibility(R.id.reset_button);

        input_hour=(EditText)getView().findViewById(R.id.edit_hour);
        input_min=(EditText)getView().findViewById(R.id.edit_min);
        input_sec=(EditText)getView().findViewById(R.id.edit_sec);

        timer=(TextView)getView().findViewById(R.id.timer_text);

        NumFilter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                Pattern ps=Pattern.compile("^[0-9]+$");
                if(!ps.matcher(charSequence).matches()){
                    return "";
                }
                return null;
            }
        };
        input_hour.setFilters(new InputFilter[] {NumFilter, new InputFilter.LengthFilter(2)});
        input_min.setFilters(new InputFilter[] {NumFilter, new InputFilter.LengthFilter(2)});
        input_sec.setFilters(new InputFilter[] {NumFilter, new InputFilter.LengthFilter(2)});
    }

    View.OnClickListener start=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String hour=addZero(input_hour.getText().toString());
            String min=addZero(input_min.getText().toString());
            String sec=addZero(input_sec.getText().toString());
            timer.setText(hour+" : "+min+" : "+sec);
            int fulltime=(Integer.parseInt(hour)*60*60*1000)+(Integer.parseInt(min)*60*1000)+(Integer.parseInt(sec)*1000);
            startTimer(fulltime);
            startstopVisibility();
            flag=true;
        }
    };

    View.OnClickListener stop=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            button=(Button)getView().findViewById(R.id.stop_button);
            if (flag) {
                countDownTimer.cancel();
                TimeSave=timer.getText().toString();
                button.setText("Restart");
                flag=false;
            } else {
                int savedTime=getIntTime(TimeSave)*1000;
                startTimer(savedTime);
                button.setText("Stop");
                flag=true;
            }
        }
    };

    View.OnClickListener reset=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            countDownTimer.cancel();
            stopTimer();
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

    public void TimeGoes(int time){
        int hour=time/(60*60);
        int min=(time%(60*60))/60;
        int sec=time%60;
        if (sec>=0) {
        } else {
            if (min>=0) {
                min-=1;
                sec=59;
            } else {
                if (hour>=0) {
                    hour-=1;
                    min=59;
                    sec=59;
                }
            }
        }
        timer.setText(addZero(Integer.toString(hour))+" : "+addZero(Integer.toString(min))+" : "+addZero(Integer.toString(sec)));
    }

    private void changeViewVisibility(int id){
        View Target=(View)getView().findViewById(id);
        if (Target.getVisibility()==View.VISIBLE) {
            Target.setVisibility(View.INVISIBLE);
        } else {
            Target.setVisibility(View.VISIBLE);
        }
    }

    private void startstopVisibility(){
        changeViewVisibility(R.id.start_button);
        changeViewVisibility(R.id.stop_button);
        changeViewVisibility(R.id.reset_button);
        changeViewVisibility(R.id.edit_hour);
        changeViewVisibility(R.id.edit_sec);
        changeViewVisibility(R.id.edit_min);
        changeViewVisibility(R.id.textView);
        changeViewVisibility(R.id.textView2);
    }

    private void stopTimer(){
        startstopVisibility();
        timer.setText("00 : 00 : 00");
        input_hour.setText("");
        input_min.setText("");
        input_sec.setText("");
        flag=false;
    }

    private void startTimer(int fulltime){
        final int t=fulltime;
        countDownTimer=new CountDownTimer(t, 1000) {
            int time=t/1000;
            @Override
            public void onTick(long l) {
                TimeGoes(time);
                time--;
            }
            @Override
            public void onFinish() {
                stopTimer();
            }
        }.start();
    }

    private int getIntTime(String time){
        String[] array=time.split(" : ");
        int intTime=(Integer.parseInt(array[0])*60*60)+(Integer.parseInt(array[1])*60)+(Integer.parseInt(array[2]));
        return intTime;
    }
}