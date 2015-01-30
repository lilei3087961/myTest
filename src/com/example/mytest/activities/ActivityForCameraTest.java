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
        // ����Cameraʵ��
        mCamera = getCameraInstance();
        Log.i("lilei", "onCreate() mCamera:"+mCamera);
        boolean isInitLayout2 = false;
        if(isInitLayout2){
        	initLayout2(mCamera);
        	return;
        }
        // ����Preview view��������Ϊactivity�е�����
        mPreview = new CameraPreview(this, mCamera);
        mPreview.setSurfaceTextureListener(mPreview);
        //���û���
        mPreview.setAlpha(0.5f);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        // preview.setAlpha(0.0f);
        preview.addView(mPreview);
        // ��Capture��ť�м���listener
        btnCapture = (Button) findViewById(R.id.button_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ������ͷ��ȡͼƬ
                mCamera.takePicture(null, null, mPictureCallback);
            }
        });
        btnReset = (Button) findViewById(R.id.button_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ������ͷ��ȡͼƬ
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

    /** ��ȫ��ȡCamera����ʵ���ķ��� */

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // ��ͼ��ȡCameraʵ��
        }
        catch (Exception e) {
            // ����ͷ�����ã�����ռ�û򲻴��ڣ�
        	Log.i(TAG, "getCameraInstance() e:"+Log.getStackTraceString(e));
        }
        return c; // �������򷵻�null
    }

    
    /** Ϊ����ͼƬ����Ƶ����File */
    private static File getOutputMediaFile(int type) {
        // ��ȫ�������ʹ��ǰӦ��
        // ��Environment.getExternalStorageState()���SD���Ƿ���װ��
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        // �������ͼƬ��Ӧ�ó���ж�غ󻹴��ڡ����ܱ�����Ӧ�ó�����
        // ��˱���λ�������
        // ��������ڵĻ����򴴽��洢Ŀ¼
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
            Log.d("MyCameraApp", "failed to create directory");
        }
        // ����ý���ļ���
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
                // �����ͼ�񱣴浽Intentָ����fileUri
                Toast.makeText(this, "Image saved to:\n" + data.getData(),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // �û�ȡ����ͼ�񲶻�
            } else {
                // ͼ�񲶻�ʧ�ܣ���ʾ�û�
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // �������Ƶ���浽Intentָ����fileUri
                Toast.makeText(this, "Video saved to:\n" + data.getData(),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // �û�ȡ������Ƶ����
            } else {
                // ��Ƶ����ʧ�ܣ���ʾ�û�
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera(); // ����ͣ�¼��������ͷ�����ͷ
    }

    private void releaseCamera() {
        if (mCamera != null) {
        	mCamera.stopPreview();
            mCamera.release(); // Ϊ����Ӧ���ͷ�����ͷ
            mCamera = null;
        }
    }
}
