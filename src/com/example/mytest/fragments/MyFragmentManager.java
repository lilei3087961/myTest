package com.example.mytest.fragments;

import com.example.mytest.R;
import com.example.mytest.R.layout;
import com.example.mytest.fragments.MyFragmentManager.Listener;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragmentManager {
	static Listener mListener;
    public static class SetCameraFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View rootView = inflater.inflate(R.layout.fragment_setting_camera,container, false);
			return rootView;
		}
    }
    
    public static class SetVideoFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View rootView = inflater.inflate(R.layout.fragment_setting_video,container, false);
			return rootView;
		}
    }
    
    public static class SetCommonFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View rootView = inflater.inflate(R.layout.fragment_setting_common,container, false);
			return rootView;
		}
    }
	
	
	
	/**
     * A placeholder fragment containing a simple view.
     */
	public static interface Listener{
		void initTabView();
	}
    public static class MainFragment extends Fragment {
    	public MainFragment(Listener listener) {
			// TODO Auto-generated constructor stub
    		mListener = listener;
		}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			mListener.initTabView();
			
		}
    }
    public static class OnScreenHintFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_on_screen_hint, container, false);
			return rootView;
		}
    }

    
    
}
