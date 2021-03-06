package com.example.mytest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class MyWeb {
	static final String TAG ="lilei";
	static String mStrUrl;
	static URL mUrl;
	static HttpURLConnection mHttpURLConnection;
	static int mResponseCode;
	static BufferedReader mBufferedReader;
	static List<HashMap<String, Object>> mMapList;
	static HashMap<String, Object> mHashMap;
	//获取服务端数据库,通过 HttpURLConnection
	public static List<HashMap<String, Object>>  getWebLogin(String name) {
		String result = "";
		mMapList = new ArrayList<HashMap<String,Object>>();
		InputStream is = null;
		try {
			mStrUrl = "http://adam.icobbler.com/getLogin.php"+((name == null || name.equals("") )? "":"?name="+name);
			mUrl = new URL(mStrUrl);
			mHttpURLConnection = (HttpURLConnection)mUrl.openConnection();
			mResponseCode = mHttpURLConnection.getResponseCode();
			if(mResponseCode == HttpURLConnection.HTTP_OK){
				is = mHttpURLConnection.getInputStream();
			}
		} catch (Exception e) {
			Log.e(TAG, "Error in http connection " + e.toString());
		}
		// 将转化为InputStream String
		try {
			mBufferedReader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = mBufferedReader.readLine()) != null) {
				Log.i("lilei", "line:"+line);
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.i("lilei", "result:"+result);
		} catch (Exception e) {
			Log.e(TAG, "Error converting result " + e.toString());
		}
		// 将String通过JSONArray解析成最终结果
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
	//获取服务端数据库,通过 Apache 获取 HttpClient
	public static List<HashMap<String, Object>>  getWebLogin2(String name) {
			String result = "";
			mStrUrl = "http://adam.icobbler.com/getLogin.php"+((name == null || name.equals("") )? "":"?name="+name);
			mMapList = new ArrayList<HashMap<String,Object>>();
			// 首先使用NameValuePair封装将要查询的年数和关键字绑定
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("year", "1980"));
			// 使用HttpPost封装整个SQL语句
			// 使用HttpClient发送HttpPost对象
			InputStream is = null;
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(mStrUrl);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			} catch (Exception e) {
				Log.e(TAG, "Error in http connection " + e.toString());
			}
			// 将HttpEntity转化为String
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
			// 将String通过JSONArray解析成最终结果
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
	//获取网络ip
	public static String GetNetIp(){
	    URL infoUrl = null;
	    InputStream inStream = null;
	    try{
	    	//http://www.ip138.com/
	        infoUrl = new URL("http://1111.ip138.com/ic.asp");//http://iframe.ip138.com/ic.asp
	        URLConnection connection = infoUrl.openConnection();
	        HttpURLConnection httpConnection = (HttpURLConnection)connection;
	        int responseCode = httpConnection.getResponseCode();
	        if(responseCode == HttpURLConnection.HTTP_OK){
	            inStream = httpConnection.getInputStream();
	            //gbk follow web encode
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"gbk"));
	            StringBuilder strber = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null){
	                strber.append(line + "\n");
	            }
	            inStream.close();
	            //从反馈的结果中提取出IP地址
	            int start = strber.indexOf("[");
	            int end = strber.indexOf("]", start + 1);
	            line = strber.substring(start + 1, end);
	            return line; 
	        }
	    }
	    catch(MalformedURLException e) {
	        e.printStackTrace();
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
