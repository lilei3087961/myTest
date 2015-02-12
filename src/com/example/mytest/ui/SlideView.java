package com.example.mytest.ui;

import com.example.mytest.R;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SlideView extends RelativeLayout {

	ImageView sideImage;
	ImageView image1;
	View mSView;
	boolean isImageShowing = true;
	public SlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i("lilei", "SlideView.onTouchEvent()");
		return super.onTouchEvent(event);
	}
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		mSView = (View)findViewById(R.id.slide_view);
		image1 = (ImageView)findViewById(R.id.image1);
		sideImage = (ImageView)findViewById(R.id.slide_image);
		sideImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isImageShowing){
					doAnimate(true);
					isImageShowing = false;
				}else{
					doAnimate(false);
					isImageShowing = true;
				}
			}
		});
	}
	void doAnimate(final boolean isToHide){
		Log.i("lilei", "doAnimate isToHide:"+isToHide);
		TranslateAnimation transY;
		if(isToHide)
			transY = new TranslateAnimation(0, 0, 0, -70);
		else
			transY = new TranslateAnimation(0, 0, -150, 0);
		//ObjectAnimator translateY = ObjectAnimator.ofFloat(this,"translationY",0,);
		mSView.startAnimation(transY);
		transY.setDuration(2000);
		transY.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				sideImage.setClickable(false);
				if(!isToHide)
					showView();
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				if(isToHide)
					hideView();
				sideImage.setClickable(true);
			}
		});
		transY.start();
	}
	void showView(){
		Log.i("lilei", "showView");
		image1.setVisibility(View.VISIBLE);
		sideImage.setImageResource(R.drawable.yixi_image_to_hide);
	}
	void hideView(){
		Log.i("lilei", "hideView");
		image1.setVisibility(View.GONE);
		sideImage.setImageResource(R.drawable.yixi_image_to_show);
	}
	
}
