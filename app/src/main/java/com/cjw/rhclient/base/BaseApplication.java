package com.cjw.rhclient.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.baidu.mapapi.SDKInitializer;

import java.util.Stack;

public class BaseApplication extends Application {
	private static Context context;
	private static Handler handler;
	private static int mainThreadId;
	private static Stack<Activity> mStack = new Stack<>();
//	private static String baseUrl = "http://10.0.3.2:8090/mipserver/rh/";
	private static String baseUrl = "http://cjw2529275344.free.ngrok.cc/mipserver/rh/";

	@Override
	public void onCreate() {
		super.onCreate();

		context = getApplicationContext();
		handler = new Handler();
		mainThreadId = android.os.Process.myTid();

		SDKInitializer.initialize(getApplicationContext());//百度地图api,我们建议该方法放在Application的初始化方法中
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		BaseApplication.baseUrl = baseUrl;
	}

	public static Context getContext() {
		return context;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static int getMainThreadId() {
		return mainThreadId;
	}

	public static void putActivity(Activity activity) {
		mStack.push(activity);
	}

	public static void removeActivity(Activity activity) {
		mStack.remove(activity);
	}

	public static Activity getActivity() {
		return mStack.get(mStack.size() - 1);
	}
}
