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
	Workspace mWorkspace;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_page_view);
		Log.i("lilei", "ActivityForPageView.onCreate");
		mWorkspace = (Workspace)findViewById(R.id.workspace);
		mWorkspace.syncPages();
	}
	
}
