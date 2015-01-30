package com.example.mytest.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mytest.R;
import com.example.mytest.ui.SlideView;

public class ActivityForImageAnimate extends Activity {
    ImageView mImageView;
    Button btnAnimate;
    SlideView mSlideView;
    ViewGroup mRootLayout;
    AnimatorSet mCaptureAnimator;
    int SHRINK_DURATION = 4000;
    int SLIDE_DURATION = 1100;
    int HOLD_DURATION = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_image_animate);
        initView();
    }
    void initView(){
        mImageView = (ImageView)findViewById(R.id.image1);
        btnAnimate = (Button)findViewById(R.id.btnAnimate);
        btnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                captureAnimate(mImageView);
            }
        });
        mRootLayout = (ViewGroup)findViewById(R.id.frame_root);
        LayoutInflater inflater = getLayoutInflater();
        mSlideView = (SlideView)inflater.inflate(R.layout.slide_layout_view, null);
        mRootLayout.addView(mSlideView);
    }
    private void captureAnimate(final View view){
        View parentView = (View) view.getParent();
        float slideDistance = (float) (parentView.getWidth() - view.getLeft());

        float scaleX = ((float) parentView.getWidth()) / ((float) view.getWidth());
        float scaleY = ((float) parentView.getHeight()) / ((float) view.getHeight());
        float scale = scaleX > scaleY ? scaleX : scaleY;

        int centerX = view.getLeft() + view.getWidth() / 2;
        int centerY = view.getTop() + view.getHeight() / 2;
        android.util.Log.i("lilei","startCaptureAnimation view.getWidth():"+view.getWidth()+" view.getHeight():"+view.getHeight()
                +" view.getLeft():"+view.getLeft()+" view.getTop():"+view.getTop()+
                " parentView.getWidth():"+parentView.getWidth()+" parentView.getHeight():"+parentView.getHeight());
        ObjectAnimator slide = ObjectAnimator.ofFloat(view, "translationX", 0f, slideDistance)
                .setDuration(SLIDE_DURATION);
        slide.setStartDelay(SHRINK_DURATION + HOLD_DURATION);

        ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY",
                parentView.getHeight() / 2 - centerY, 0f)
                .setDuration(SHRINK_DURATION);
        ObjectAnimator translateY2 = ObjectAnimator.ofFloat(view, "translationY",
                parentView.getHeight() / 2 - centerY, -100f)
                .setDuration(SHRINK_DURATION);
        translateY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                // Do nothing.
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // Do nothing.
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                // Do nothing.
            }
        });

        mCaptureAnimator = new AnimatorSet();
        mCaptureAnimator.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", scaleX, 1f)
                        .setDuration(SHRINK_DURATION),
                ObjectAnimator.ofFloat(view, "scaleY", scale, 1f)
                        .setDuration(SHRINK_DURATION),
                ObjectAnimator.ofFloat(view, "translationX",
                        parentView.getWidth() / 2 - centerX, 0f)
                        .setDuration(SHRINK_DURATION),
                translateY);  //modify by lilei
        //mCaptureAnimator.playTogether(translateY2);
        mCaptureAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setClickable(false);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setScaleX(1f);
                view.setScaleX(1f);
                view.setTranslationX(0f);
                view.setTranslationY(0f);
                //view.setVisibility(View.INVISIBLE);  //modify by lilei
                mCaptureAnimator.removeAllListeners();
                mCaptureAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //view.setVisibility(View.INVISIBLE);  //modify by lilei
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                // Do nothing.
            }
        });
        mCaptureAnimator.start();
        
    }
}
