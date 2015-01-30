package com.example.mytest.activities;


import android.app.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.mytest.R;
import com.example.mytest.camera.CameraPreview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class ActivityForCameraTest extends Activity {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Camera mCamera;
    private CameraPreview mPreview;
    Button btnCapture;
    Button btnReset;
    private static final String TAG = "lilei";
    private PictureCallback mPictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d(TAG,"Error creating media file, check storage permissions: "+ "e.getMessage()");
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textureview);
        // 创建Camera实例
        mCamera = getCameraInstance();
        Log.i("lilei", "onCreate() mCamera:"+mCamera);
        boolean isInitLayout2 = false;
        if(isInitLayout2){
        	initLayout2(mCamera);
        	return;
        }
        // 创建Preview view并将其设为activity中的内容
        mPreview = new CameraPreview(this, mCamera);
        mPreview.setSurfaceTextureListener(mPreview);
        //设置浑浊
        mPreview.setAlpha(0.5f);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        // preview.setAlpha(0.0f);
        preview.addView(mPreview);
        // 在Capture按钮中加入listener
        btnCapture = (Button) findViewById(R.id.button_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从摄像头获取图片
                mCamera.takePicture(null, null, mPictureCallback);
            }
        });
        btnReset = (Button) findViewById(R.id.button_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从摄像头获取图片
                //mCamera.takePicture(null, null, mPictureCallback);
            	mCamera.stopPreview();
            	mCamera.startPreview();
            }
        });
    }
    SurfaceTexture mSurfaceTexture = null;
    void initLayout2(Camera mDevice){
    	try {
			mDevice.setPreviewTexture(mSurfaceTexture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /** 安全获取Camera对象实例的方法 */

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // 试图获取Camera实例
        }
        catch (Exception e) {
            // 摄像头不可用（正被占用或不存在）
        	Log.i(TAG, "getCameraInstance() e:"+Log.getStackTraceString(e));
        }
        return c; // 不可用则返回null
    }

    
    /** 为保存图片或视频创建File */
    private static File getOutputMediaFile(int type) {
        // 安全起见，在使用前应该
        // 用Environment.getExternalStorageState()检查SD卡是否已装入
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        // 如果期望图片在应用程序卸载后还存在、且能被其它应用程序共享，
        // 则此保存位置最合适
        // 如果不存在的话，则创建存储目录
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
            Log.d("MyCameraApp", "failed to create directory");
        }
        // 创建媒体文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 捕获的图像保存到Intent指定的fileUri
                Toast.makeText(this, "Image saved to:\n" + data.getData(),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了图像捕获
            } else {
                // 图像捕获失败，提示用户
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 捕获的视频保存到Intent指定的fileUri
                Toast.makeText(this, "Video saved to:\n" + data.getData(),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了视频捕获
            } else {
                // 视频捕获失败，提示用户
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera(); // 在暂停事件中立即释放摄像头
    }

    private void releaseCamera() {
        if (mCamera != null) {
        	mCamera.stopPreview();
            mCamera.release(); // 为其它应用释放摄像头
            mCamera = null;
        }
    }
}
