package com.example.mytest.activities;


import java.lang.reflect.Array;

import com.example.mytest.MainActivity;
import com.example.mytest.R;
import com.example.mytest.fragments.MyFragmentManager;
import com.example.mytest.fragments.MyFragmentManager.MainFragment;
import com.example.mytest.ui.OnScreenHint;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TabHost;

public class ActivityForTabHostTest extends Activity{
	private TabHost tabHost=null;
    private PopupWindow mPopup;
    private Button btnshow;
    ListView mSettingListCamera;
    ListView mSettingListVideo;
    String[] mStrArray1;
    String[] mStrArray2;
    FragmentManager fragmentManager = getFragmentManager();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_tabhost);
		initViews();
	}
	public void btnshowOnClick(View view){
		Log.i("lilei","btnshowOnClick");
		//initViews();
		showPopup();
	}
	void initViews(){
		btnshow = (Button)findViewById(R.id.btnshow);
		
		LayoutInflater mLayoutInflater = LayoutInflater.from(ActivityForTabHostTest.this);
		View rootViewTabHost = mLayoutInflater.inflate(R.layout.yixi_simple_tabhost_camera, null);
		tabHost=(TabHost)rootViewTabHost.findViewById(R.id.tabhost_camera);
		mSettingListCamera = (ListView) rootViewTabHost.findViewById(R.id.setting_list_camera);
		mSettingListVideo = (ListView) rootViewTabHost.findViewById(R.id.setting_list_video);
		mStrArray1 = getResources().getStringArray(R.array.array_camera);
		mStrArray2 = getResources().getStringArray(R.array.array_video);
		ArrayAdapter<String> arraryAdapter1= new ArrayAdapter<String>(this,R.layout.simple_list_item_textview,mStrArray1);
		ArrayAdapter<String> arraryAdapter2= new ArrayAdapter<String>(this,R.layout.simple_list_item_textview,mStrArray2);
		mSettingListCamera.setAdapter(arraryAdapter1);
		mSettingListVideo.setAdapter(arraryAdapter2);
		//View view = (View)rootView.findViewById(R.id.info_include01);
		View rootViewTextview1 = mLayoutInflater.inflate(R.layout.simple_textview1, null);
		View view = (View)rootViewTextview1.findViewById(R.id.txt1);
		
		if(view == null || tabHost == null){
			Log.i("lilei","view:"+view);
			return;
		}
    	tabHost.setup(); //使用布局的TabHost 需添加
	
    	tabHost.addTab(tabHost.newTabSpec("camera")
    	             .setContent(R.id.frame_camera)
    	             .setIndicator("camera",getResources().getDrawable(R.drawable.ic_launcher))
    	            		
    	);
    	tabHost.addTab(tabHost.newTabSpec("video")
	             .setContent(R.id.frame_video)
	             .setIndicator("video",getResources().getDrawable(R.drawable.ic_launcher))   		
        );
		
	}
	public void showPopup(){
		mPopup = new PopupWindow(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopup.setOutsideTouchable(true);
        mPopup.setFocusable(true);
        mPopup.setContentView(tabHost);
        mPopup.showAtLocation(btnshow, Gravity.CENTER_HORIZONTAL, 0, 0);
	}
}