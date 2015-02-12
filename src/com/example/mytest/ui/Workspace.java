/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mytest.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.example.mytest.R;

/**
 * The workspace is a wide area with a wallpaper and a finite number of pages.
 * Each page contains a number of icons, folders or widgets the user can
 * interact with. A workspace is meant to be used with a fixed width only.
 */
public class Workspace extends ViewGroup implements View.OnTouchListener,ViewGroup.OnHierarchyChangeListener {
	ViewGroup mLayout1;
	ViewGroup mLayout2;
	Context mContext;
	Button mButton;
	Button btnLeft;
	Button btnRight;
	public Workspace(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	public void syncPages(){
		LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
		mLayoutInflater.inflate(R.layout.workspace_screen, null);
		mLayout1 = (ViewGroup)mLayoutInflater.inflate(R.layout.workspace_screen, null);
		mLayout2 = (ViewGroup)mLayoutInflater.inflate(R.layout.workspace_screen1, null);
		btnLeft = (Button)mLayout2.findViewById(R.id.btn_left);
		btnLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				srollRight(null);
				//srollLeft(null);
			}
		});
		btnRight =  (Button)mLayout2.findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				srollLeft(null);
				//srollRight(null);
			}
		});
		Log.i("lilei","syncPages() mLayout1:"+mLayout1+" mLayout2:"+mLayout2);
		this.addView(mLayout2);
		this.addView(mLayout1);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		 Log.i("liliei", "~~00 onInterceptTouchEvent()");
		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		 Log.i("lilei", "~~11 onTouchEvent()");
		 return super.onTouchEvent(event);
	}
	@Override
	public void onChildViewAdded(View arg0, View arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildViewRemoved(View arg0, View arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		int action = arg1.getAction();
		Log.i("liliei", "onTouch() action:"+action);
		if(action == MotionEvent.ACTION_UP){
			srollRight(null);
		}
		return false;
	}

	public void srollLeft(View view){
		this.scrollBy(100, 0);
		Log.i("lilei", "srollLeft");
	}
	public void srollRight(View view){
		this.scrollBy(-100, 0);
		Log.i("lilei", "srollRight");
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		Log.i("lilei", "onMeasure:");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int count = getChildCount();
        for (int i = 0; i < count; i++) {
                 getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
	}
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		Log.i("lilei", "arg0:"+arg0+" arg1:"+arg1+" arg2:"+arg2+" arg3:"+arg3+" arg4:"+arg4);
		int childLeft = 0;
		int width =  arg3;
		for(int i=0;i<getChildCount();i++){
			View child = getChildAt(i);
			child.layout(childLeft, arg2, childLeft+width, arg4);
			Log.i("lilei", "i:"+i+" getMeasuredWidth:"+child.getMeasuredWidth()+" getWidth:"+child.getWidth()+
					" childLeft:"+childLeft);
			childLeft += width;

		}
		//super.onLayout(arg0, arg1, arg2, arg3, arg4);
	}
 
}
