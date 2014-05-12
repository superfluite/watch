package com.example.stpntimer.app;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    final String TAG="MainActivity";

    String[] menus={"Stop Watch", "Timer"};
    ListView drawlist;

    int curFragindex;
    public final static int STPWATCH=0;
    public final static int TIMER=1;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawlist=(ListView)findViewById(R.id.drawlist);
        drawlist.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menus));
        drawlist.setOnItemClickListener(new MenuClick());

        drawer=(DrawerLayout)findViewById(R.id.drawer_list);
        curFragindex=STPWATCH;
        changeFrag(curFragindex);
    }
    public void changeFrag(int index){
        Fragment frag=null;
        frag=getFrag(index);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container,frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public Fragment getFrag(int index){
        Fragment frag=null;
        switch (index){
            case STPWATCH:
                frag=new StpWatchFrag();
                break;
            case TIMER:
                frag=new TimerFrag();
                break;
            default:
                break;
        }
        return frag;
    }

    private class MenuClick implements ListView.OnItemClickListener{
        public void onItemClick(AdapterView<?> adapter, View v, int pos, long id){
            switch(pos){
                case 0:
                    curFragindex=STPWATCH;
                    changeFrag(curFragindex);
                    break;
                case 1:
                    curFragindex=TIMER;
                    changeFrag(curFragindex);
                    break;
            }
        }
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


}
