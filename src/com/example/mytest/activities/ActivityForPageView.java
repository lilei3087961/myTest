package com.example.mytest.activities;

import com.example.mytest.R;
import com.example.mytest.ui.Workspace;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;

public class ActivityForPageView extends Activity {
	ViewGroup mRoot;
	Button mButton;
	Button btnLeft;
	Button btnRight;
	ViewGroup mLayout1;
	ViewGroup mLayout2;
	Workspace mWorkspace;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_page_view);
		Log.i("lilei", "ActivityForPageView.onCreate");
		mWorkspace = (Workspace)findViewById(R.id.workspace);
		mWorkspace.syncPages();
		//syncPages();
	}
	public void syncPages(){
		LayoutInflater mLayoutInflater = LayoutInflater.from(getApplicationContext());
		mLayoutInflater.inflate(R.layout.workspace_screen, null);
		mLayout1 = (ViewGroup)mLayoutInflater.inflate(R.layout.workspace_screen, null);
		mLayout2 = (ViewGroup)mLayoutInflater.inflate(R.layout.workspace_screen1, null);
		btnLeft = (Button)mLayout2.findViewById(R.id.btn_left);
		btnLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				srollLeft(null);
			}
		});
		btnRight =  (Button)mLayout2.findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				srollRight(null);
			}
		});
		Log.i("lilei","syncPages() mLayout1:"+mLayout1+" mLayout2:"+mLayout2);
		mButton = new Button(this);
		mButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mButton.setText("scrll left");
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Log.i("liliei", "~~00 mButton.onClick()");
			}
		});
		mWorkspace.addView(mButton);
		mWorkspace.addView(mLayout2);
		mWorkspace.addView(mLayout1);
	}
	void initView(){
		LayoutInflater mLayoutInflater = getLayoutInflater();
		mLayoutInflater.inflate(R.layout.workspace_screen, null);
		mLayout1 = (ViewGroup)mLayoutInflater.inflate(R.layout.workspace_screen, null);
		mLayout2 = (ViewGroup)mLayoutInflater.inflate(R.layout.workspace_screen1, null);
/*		mRoot = (ViewGroup)findViewById(R.id.launcher);
		Log.i("lilei","mLayout1:"+mLayout1+" mLayout2:"+mLayout2);
		mRoot.addView(mLayout1);
		mRoot.addView(mLayout2); */
	}

	public void srollLeft(View view){
		for(int i=0;i<mRoot.getChildCount();i++){
			Log.i("lilei", "i:"+i+" getLeft:"+mRoot.getChildAt(i).getLeft()
					+" getWidth:"+mRoot.getChildAt(i).getWidth());
		}
		mRoot.scrollBy(-100, 0);
		Log.i("lilei", "srollLeft");
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i("lilei", "onKeyDown");
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i("lilei", "onKeyUp");
		return super.onKeyUp(keyCode, event);
	}
	public void srollRight(View view){
		for(int i=0;i<mRoot.getChildCount();i++){
			Log.i("lilei", "i:"+i+" getLeft:"+mRoot.getChildAt(i).getLeft()
					+" getWidth:"+mRoot.getChildAt(i).getWidth());
		}
		mRoot.scrollBy(100, 0);
		Log.i("lilei", "srollRight");
	}
}
