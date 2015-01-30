package com.example.mytest.camera;

import java.io.IOException;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

public class CameraPreview extends TextureView implements
		TextureView.SurfaceTextureListener {
	private Camera mCamera;
	private TextureView mTextureView;
	public CameraPreview(Context context , Camera camera) {
		super(context);
		mCamera = camera;
		// TODO Auto-generated constructor stub
	}
	
	
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
	    int height) {
		//mCamera = Camera.open();
		try {
			if(mCamera == null)
				return;
		    mCamera.setPreviewTexture(surface);
		    Camera.CameraInfo info = new Camera.CameraInfo();
		    int cameraId = 0;
	        Camera.getCameraInfo(cameraId, info);
		    Log.i("lilei", "onSurfaceTextureAvailable info.orientation:"+info.orientation);
		    mCamera.setDisplayOrientation(info.orientation);
		    mCamera.startPreview();
		} catch (IOException ioe) {
		    // Something bad happened
		}
	}
	
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
	    int height) {
		// Ignored, Camera does all the work for us
	}
	
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

		//mCamera.stopPreview();
		//mCamera.release();
		return true;
	}
	
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// Invoked every time there's a new Camera preview frame
	}

}
