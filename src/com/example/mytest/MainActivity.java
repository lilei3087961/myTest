package com.example.mytest;


import com.example.mytest.activities.ActivityForCameraTest;
import com.example.mytest.activities.ActivityForFragmentTest;
import com.example.mytest.activities.ActivityForImageAnimate;
import com.example.mytest.activities.ActivityForLedFlash;
import com.example.mytest.activities.ActivityForTabHostTest;
import com.example.mytest.fragments.MyFragmentManager.MainFragment;
import com.example.mytest.ui.OnScreenHint;

import java.io.File;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MainActivity extends ActionBarActivity{
	ListView mListView;
	String[] mStrArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//setContentView(R.layout.activity_main);
        initViews();
        new Button(this).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ImageView image = null;
				image.getDrawable();
				image.setImageDrawable(image.getDrawable());
			}
		});
    }
    void initViews(){
    	mListView = (ListView)findViewById(R.id.list_main);
        mStrArray = getResources().getStringArray(R.array.array_test);
        ArrayAdapter<String> arraryAdapter= new ArrayAdapter<String>(this,R.layout.simple_list_item_textview,mStrArray);
        if(mListView == null || arraryAdapter == null){
        	Log.i("lilei","MainActivity mListView:"+mListView+" arraryAdapter:"+arraryAdapter);
        	return;
        }
        mListView.setAdapter(arraryAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch(arg2){
					case 0:
						Intent intent=new Intent(MainActivity.this,ActivityForTabHostTest.class);
						startActivity(intent);
						break;
					case 1:
						intent = new Intent(MainActivity.this,ActivityForCameraTest.class);
						startActivity(intent);
						break;
					case 2:
						intent = new Intent(MainActivity.this,ActivityForFragmentTest.class);
						startActivity(intent);
						break;
					case 3:
                        intent = new Intent(MainActivity.this,ActivityForImageAnimate.class);
                        startActivity(intent);
                        break;
					case 4:
                        intent = new Intent(MainActivity.this,ActivityForLedFlash.class);
                        startActivity(intent);
                        break;
					default:
						break;
				}
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
	

}
