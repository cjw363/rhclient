package com.cjw.rhclient.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.cjw.rhclient.been.User;

import java.util.Stack;

public class BaseApplication extends Application {
	private static Context context;
	private static Handler handler;
	private static int mainThreadId;
	private static Stack<Activity> mStack = new Stack<Activity>();
//	private static String baseUrl = "http://10.0.3.2:8090/mipserver/rh/";
	private static String baseUrl = "http://cjw2529275344.free.ngrok.cc/mipserver/rh/";
	private static User user;

	@Override
	public void onCreate() {
		super.onCreate();

		context = getApplicationContext();
		handler = new Handler();
		mainThreadId = android.os.Process.myTid();
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		BaseApplication.user = user;
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

	public static void removeActivity() {
		mStack.pop();
	}

	public static Activity getActivity() {
		return mStack.get(mStack.size() - 1);
	}
}
