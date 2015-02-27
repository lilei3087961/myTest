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
public class Workspace extends ViewGroup{
	ViewGroup mLayout1;
	ViewGroup mLayout2;
	Context mContext;
	Button mButton;
	Button btnLeft;
	Button btnRight;
	static int mCurrentPage = 0;
	int mScrollX;
	float mDownMotionX;
	float mLastMotionX;
	float mUpMotionX;
	float mTotalMotionX;
	float mLastMotionXRemainder;
    static final int INVALID_POINTER = -1;
    int mActivePointerId = INVALID_POINTER;
	static final String TAG = "lilei"; 
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
				srollLeft();
				//srollLeft(null);
			}
		});
		btnRight =  (Button)mLayout2.findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				srollRight();
				//srollRight(null);
			}
		});
		Log.i(TAG,"syncPages() mLayout1:"+mLayout1+" mLayout2:"+mLayout2);
		this.addView(mLayout2);
		this.addView(mLayout1);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		final int action = ev.getAction();
		 String state = "";
	        int mask = action & MotionEvent.ACTION_MASK;
	        switch (mask){
	        case MotionEvent.ACTION_DOWN:
                mTotalMotionX = 0;
	        	state = "ACTION_DOWN";
	        	break;
	        case MotionEvent.ACTION_MOVE:
	        	state = "ACTION_MOVE";
	        	break;
	        case MotionEvent.ACTION_UP:
	        	state = "ACTION_UP";
	        	break;
	        }
	        //boolean result = super.onInterceptTouchEvent(ev);
		 Log.i(TAG, "onInterceptTouchEvent() false mask:"+mask+" state:"+state);//+" return:"+result);
		return false;//super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		 final int action = event.getAction();
		 switch (action & MotionEvent.ACTION_MASK) {
        	case MotionEvent.ACTION_DOWN:
        		mActivePointerId = event.getPointerId(0);
        		mDownMotionX = mLastMotionX = event.getX();
        		
        		break;
        	case MotionEvent.ACTION_MOVE:
        		initiateScroll(event);
        		break;
        	case MotionEvent.ACTION_UP:
        		final int activePointerId = mActivePointerId;
                final float x = event.getX(activePointerId);
                final int deltaX = (int) (x - mDownMotionX);
                srollPage(deltaX);
        		break;
        	
		 }
		 String state = "";
        int mask = action & MotionEvent.ACTION_MASK;
        switch (mask){
	        case MotionEvent.ACTION_DOWN:
	        	state = "ACTION_DOWN";
	        	break;
	        case MotionEvent.ACTION_MOVE:
	        	state = "ACTION_MOVE";
	        	break;
	        case MotionEvent.ACTION_UP:
	        	state = "ACTION_UP";
	        	break;
        }
        //boolean result = super.onTouchEvent(event);
		 Log.i(TAG, "~~11 onTouchEvent() true mask:"+mask+" state:"+state+" getScrollX:"+this.getScrollX()+
				 " mTotalMotionX:"+mTotalMotionX);//+" result:"+result);
		 
		 return true;
	}
	void initiateScroll(MotionEvent ev) {
        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
        final float x = ev.getX(pointerIndex);
        final float deltaX = mLastMotionX + mLastMotionXRemainder - x;
        mTotalMotionX += Math.abs(deltaX);
        if (Math.abs(deltaX) >= 1.0f) {
        	Log.i(TAG, "initiateScroll() deltaX:"+((int) deltaX));
        	scrollBy((int) deltaX);
            mLastMotionX = x;
            mLastMotionXRemainder = deltaX - (int) deltaX;
        }
	}
	public void scrollBy(int x) {
		this.scrollBy(x,0);
	}
	public void scrollTo(int x) {
		this.scrollTo(x,0);
	}
	public void srollRight(){
		mScrollX = getChildAt(mCurrentPage).getMeasuredWidth();

		this.scrollBy(-mScrollX, 0);
		Log.i(TAG, "srollRight:-"+mScrollX);
	}
	public void srollLeft(){
		mScrollX = getChildAt(mCurrentPage).getMeasuredWidth();
		this.scrollBy(mScrollX, 0);
		
		Log.i(TAG, "srollLeft:"+mScrollX);
	}
	public void srollPage(int deltax){
		Log.i("lilei","**srollPage() mCurrentPage:"+mCurrentPage);
		int currWidth = getChildAt(mCurrentPage).getMeasuredWidth();
		int originX = (int)getChildAt(mCurrentPage).getX();
		int nextPageX;
		int prePageX;
		int currScrollX = this.getScrollX();
		int newScrollX = 0;
		Log.i("lilei","srollPage:"+deltax+" currScrollX:"+currScrollX+" originX:"+originX+" currWidth:"+currWidth);
		if(deltax>0){
			if(Math.abs(deltax)>0.4*currWidth){
				mCurrentPage = mCurrentPage-1 <= 0 ? 0:(mCurrentPage-1);
				prePageX = (int) getChildAt(mCurrentPage).getX();
				Log.i("lilei","srollPage 111 mCurrentPage:"+mCurrentPage+" prePageX:"+prePageX);
				scrollTo(prePageX);
				newScrollX = currScrollX - currWidth;
			}else{
				scrollTo(originX);
				Log.i("lilei","srollPage 222");
			}
			//srollRight();
		}else if(deltax<0){  //scroll left
			if(Math.abs(deltax)>0.4*currWidth){
				newScrollX = currWidth - currScrollX;
				mCurrentPage = mCurrentPage+1 >= getChildCount()? (getChildCount()-1):(mCurrentPage+1);
				nextPageX = (int) getChildAt(mCurrentPage).getX();
				Log.i("lilei","srollPage 333 mCurrentPage:"+mCurrentPage+" nextPageX:"+nextPageX);
				scrollTo(nextPageX);
			}else{
				Log.i("lilei","srollPage 444 originX:"+originX);
				scrollTo(originX);
				newScrollX = -currScrollX;
			}
			//srollLeft();
		}
//		Log.i("lilei","srollPage: newScrollX:"+newScrollX);
//		scrollBy(newScrollX);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onMeasure:");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int count = getChildCount();
        for (int i = 0; i < count; i++) {
                 getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
	}
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		Log.i(TAG, "arg0:"+arg0+" arg1:"+arg1+" arg2:"+arg2+" arg3:"+arg3+" arg4:"+arg4);
		int childLeft = 0;
		int width =  arg3;
		for(int i=0;i<getChildCount();i++){
			View child = getChildAt(i);
			child.layout(childLeft, arg2, childLeft+width, arg4);
			Log.i(TAG, "i:"+i+" getMeasuredWidth:"+child.getMeasuredWidth()+" getWidth:"+child.getWidth()+
					" childLeft:"+childLeft);
			childLeft += width;

		}
		//super.onLayout(arg0, arg1, arg2, arg3, arg4);
	}
 
}
