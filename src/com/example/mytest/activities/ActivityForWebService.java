package com.example.mytest.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mytest.R;
import com.example.mytest.util.MyWeb;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivityForWebService extends Activity {
	Button btnGetService;
	EditText editName;
	Handler mHandler = new MainHandler();
	Thread mThread;
	ListView mListView;
	SimpleAdapter mSimpleAdapter;
	List<HashMap<String, Object>> mMapList;
	String[] mMapKeys;
	int[] mResIds;
	private static final int UPDATE_LISTVIEW = 1;
	static final String TAG ="lilei";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_webservice);
		initView();
	}

	void initView() {
		editName = (EditText)findViewById(R.id.edit_name);
		btnGetService = (Button) findViewById(R.id.btnGetService);
		btnGetService.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stubs
				mThread = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("lilei", "editName:"+editName.getText().toString().trim());
						mMapList = MyWeb.getWebLogin(editName.getText().toString().trim());
						mHandler.sendEmptyMessage(UPDATE_LISTVIEW);
					}
				});
				mThread.start();
			}
		});
	}
	class MainHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//super.handleMessage(msg);
			switch (msg.what) {
			case UPDATE_LISTVIEW:
				updateListview();
				break;
			default:
				break;
			}
		}
	}
	void updateListview(){
		Log.i("lilei","updateListview() ");
		mMapKeys = new String[]{"id","name","sex"};
		mResIds =  new int[]{R.id.txt_id,R.id.txt_name,R.id.txt_sex};
		mSimpleAdapter = new SimpleAdapter(this, mMapList, R.layout.simple_webservice_view, mMapKeys, mResIds);
		mListView = (ListView)findViewById(R.id.list_service);
		mListView.setAdapter(mSimpleAdapter);
	}

	
}
