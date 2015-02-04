package com.example.mytest.util;

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

import android.util.Log;

public class MyWeb {
	static final String TAG ="lilei";
	static String mUrl;
	static List<HashMap<String, Object>> mMapList;
	static HashMap<String, Object> mHashMap;
	public static List<HashMap<String, Object>>  getWebLogin(String name) {
		String result = "";
		mUrl = "http://adam.icobbler.com/getLogin.php"+((name == null || name.equals("") )? "":"?name="+name);
		mMapList = new ArrayList<HashMap<String,Object>>();
		// ����ʹ��NameValuePair��װ��Ҫ��ѯ�������͹ؼ��ְ�
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("year", "1980"));
		// ʹ��HttpPost��װ����SQL���
		// ʹ��HttpClient����HttpPost����
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(mUrl);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e(TAG, "Error in http connection " + e.toString());
		}
		// ��HttpEntityת��ΪString
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.i("lilei", "result:"+result);
		} catch (Exception e) {
			Log.e(TAG, "Error converting result " + e.toString());
		}
		// ��Stringͨ��JSONArray���������ս��
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				mHashMap = new HashMap<String, Object>();
				mHashMap.put("id", json_data.getInt("id"));
				mHashMap.put("name", json_data.getString("name"));
				mHashMap.put("sex", json_data.getInt("sex"));
				mMapList.add(mHashMap);
				Log.i(TAG,"id: " + json_data.getInt("id")
						+ ", name: "+ json_data.getString("name") 
						+ ", sex: "+ json_data.getInt("sex"));
			}
		} catch (JSONException e) {
			Log.e(TAG, "Error parsing data " + e.toString());
		}
		return mMapList;
	}
}
