package com.example.mytest.activities;

import com.example.mytest.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivityForLedFlash extends Activity {
	Button mButton;
	TextView txt1;
    public final static String TAG = "lilei";
    public static final byte[] LIGHTE_ON = { '1', '2', '7' };
    public static final byte[] LIGHTE_OFF = { '0' };
    public static final byte[] LIGHT_TORCH = {'1'};
    public static final byte[] LIGHT_DEFAULT = {'0'};
    private final static String MSM8226_FLASHLIGHT_BRIGHTNESS =
            "/sys/class/leds/torch-light/brightness";
    private final static String COMMON_FLASHLIGHT_BRIGHTNESS =
            "/sys/class/leds/flashlight/brightness";
    private final static String COMMON_FLASHLIGHT_MODE =
            "/sys/class/leds/flashlight/mode";
    private boolean isLedOn = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_led_flash);
		mButton =  (Button)findViewById(R.id.btn_toggle_led);
		txt1 = (TextView)findViewById(R.id.txt1);
		txt1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		txt1.setMarqueeRepeatLimit(6);
		txt1.setSingleLine(true);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isLedOn){
					setLEDStatus(isLedOn);
					isLedOn = false;
				}else{
					setLEDStatus(isLedOn);
					isLedOn = true;
				}
				
			}
		});
	}
	private void setLEDStatus(boolean status){
		// for MSM8x26, BSP add MSM8226_TORCH_NODE for control torch brightness
        if (isFileExists(MSM8226_FLASHLIGHT_BRIGHTNESS)) {
            changeLEDFlashBrightness(status, MSM8226_FLASHLIGHT_BRIGHTNESS);
        } else {
            changeLEDFlashMode(status, COMMON_FLASHLIGHT_MODE);
            changeLEDFlashBrightness(status, COMMON_FLASHLIGHT_BRIGHTNESS);
        }
	}
	
	 private void changeLEDFlashBrightness(boolean status, String node) {
	        try {
	        	Log.d(TAG,"~~~changeLEDFlashBrightness node:"+node+" status:"+status);
	            byte[] ledData = status ? LIGHTE_ON : LIGHTE_OFF;
	            FileOutputStream brightness = new FileOutputStream(node);
	            brightness.write(ledData);
	            brightness.close();
	        } catch (FileNotFoundException e) {
	            Log.d(TAG, e.toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	 private void changeLEDFlashMode(boolean status, String node) {
	        try {
	            byte[] ledMode = status ? LIGHT_TORCH : LIGHT_DEFAULT;
	            FileOutputStream mode = new FileOutputStream(node);
	            mode.write(ledMode);
	            mode.close();
	        } catch (FileNotFoundException e) {
	            Log.d(TAG, e.toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	    private boolean isFileExists(String filePath) {
	        File file = new File(filePath);
	        return file.exists();
	    }

}
