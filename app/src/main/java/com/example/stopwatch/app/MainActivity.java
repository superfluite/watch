package com.example.stopwatch.app;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    Chronometer crnmeter;
    boolean flag=false;
    long timestop=0;
    ListView listView;
    ArrayAdapter arrayAdapter;
    int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crnmeter=(Chronometer)findViewById(R.id.chronometer);
        listView=(ListView)findViewById(R.id.listView);
        Button button;

        button=(Button)findViewById(R.id.startbtn);
        button.setOnClickListener(Start);
        button=(Button)findViewById(R.id.resetbtn);
        button.setVisibility(View.GONE);
        button.setOnClickListener(Reset);
        button=(Button)findViewById(R.id.recordbtn);
        button.setVisibility(View.GONE);
        button.setOnClickListener(Record);
        button=(Button)findViewById(R.id.stopbtn);
        button.setVisibility(View.GONE);
        button.setOnClickListener(Stop);

        arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.listview_custom);
        listView.setAdapter(arrayAdapter);

        crnmeter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long t=SystemClock.elapsedRealtime()-chronometer.getBase();
                long m=t/60000;
                long s=(t%60000)/1000;
                long ms=t%1000;
                //chronometer.setText(t+"");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener Start=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            crnmeter.setBase(SystemClock.elapsedRealtime()+timestop);
            crnmeter.start();
            flag=true;
            Button btn;
            btn=(Button)findViewById(R.id.startbtn);
            btn.setVisibility(View.GONE);
            btn=(Button)findViewById(R.id.stopbtn);
            btn.setVisibility(View.VISIBLE);
            btn=(Button)findViewById(R.id.resetbtn);
            btn.setVisibility(View.VISIBLE);
            btn=(Button)findViewById(R.id.recordbtn);
            btn.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener Reset=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            crnmeter.stop();
            crnmeter.setBase(SystemClock.elapsedRealtime());
            timestop=0;
            arrayAdapter.clear();
            Button btn;
            btn=(Button)findViewById(R.id.startbtn);
            btn.setVisibility(View.VISIBLE);
            btn=(Button)findViewById(R.id.stopbtn);
            btn.setText("STOP");
            btn.setVisibility(View.GONE);
            btn=(Button)findViewById(R.id.resetbtn);
            btn.setVisibility(View.GONE);
            btn=(Button)findViewById(R.id.recordbtn);
            btn.setVisibility(View.GONE);
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

    View.OnClickListener Stop=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn=(Button)findViewById(R.id.stopbtn);
            if(flag){
                timestop=crnmeter.getBase()-SystemClock.elapsedRealtime();
                crnmeter.stop();
                flag=false;
                btn.setText("RESTART");
                btn=(Button)findViewById(R.id.recordbtn);
                btn.setVisibility(View.GONE);
            }else{
                crnmeter.setBase(SystemClock.elapsedRealtime()+timestop);
                crnmeter.start();
                flag=true;
                btn.setText("STOP");
                btn=(Button)findViewById(R.id.recordbtn);
                btn.setVisibility(View.VISIBLE);
            }
        }
    };
}
