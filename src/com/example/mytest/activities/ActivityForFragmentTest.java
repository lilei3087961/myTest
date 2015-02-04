package com.example.mytest.activities;

import com.example.mytest.R;
import com.example.mytest.fragments.MyFragmentManager;

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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class ActivityForFragmentTest extends Activity {
	FragmentManager mFragmentManager = getFragmentManager();
	PopupWindow mPopup;
	Button btnPop;
	View popView;
	LinearLayout content1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_fragment);
		initView();
	}
	void initView(){
		btnPop =  (Button)findViewById(R.id.btn_show_pop);
		content1 =(LinearLayout)findViewById(R.id.content1);
		popView = (View)findViewById(R.id.pop_view);
		FragmentTransaction ft1 = mFragmentManager.beginTransaction();
		ft1.add(R.id.fragment_content, new MyFragmentManager.SetCameraFragment()).commit();
		
	}
	public void showPop(View view){

		LayoutInflater mLayoutInflater = getLayoutInflater();
		View mView = mLayoutInflater.inflate(R.layout.simple_textview2,null);
		//TextView tv =  (TextView)findViewById(R.id.txt2);
		Log.i("lilei", "showPop btnPop:"+btnPop+" mView:"+mView);
//		mPopup = new PopupWindow(mView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
//		mPopup.showAsDropDown(btnPop);
//		if(true)
//			return;
		if(mPopup == null){
			mPopup = new PopupWindow(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	        mPopup.setOutsideTouchable(true);
	        mPopup.setFocusable(true);
	        if(popView.getParent() != null)
	        	((ViewGroup)popView.getParent()).removeView(popView);
	        mPopup.setContentView(popView);//popView
		}
		if(!mPopup.isShowing())
			mPopup.showAsDropDown(btnPop);
        //mPopup.showAtLocation(btnPop, Gravity.TOP, 0, 0);
	}
	public void showPopAddView(View view){
		LayoutInflater mLayoutInflater = getLayoutInflater();
		//popView = mLayoutInflater.inflate(R.layout.activity_for_fragment_pop,null);
		popView = (View)findViewById(R.id.pop_view);
		((ViewGroup)popView.getParent()).removeView(popView);
		content1.addView(popView);
	}
	public void hidePop(View view){
		
		content1.removeView(popView);
	}
	public void setCameraOnclick(View view){
		Log.i("lilei", "setCameraOnclick");
		FragmentTransaction ft1 = mFragmentManager.beginTransaction();
		ft1.replace(R.id.fragment_content, new MyFragmentManager.SetCameraFragment()).commit();
	}
	public void setVideoOnclick(View view){
		Log.i("lilei", "setVideoOnclick:");
		FragmentTransaction ft1 = mFragmentManager.beginTransaction();
		ft1.replace(R.id.fragment_content, new MyFragmentManager.SetVideoFragment()).commit();
	}
	public void setCommonOnclick(View view){
		Log.i("lilei", "setCommonOnclick:");
		FragmentTransaction ft1 = mFragmentManager.beginTransaction();
		ft1.replace(R.id.fragment_content, new MyFragmentManager.SetCommonFragment()).commit();
	}

	

}
