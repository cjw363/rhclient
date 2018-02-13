package com.cjw.rhclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.base.BaseApplication;

public class UI {

	public static boolean getHasWindowFocus() {
		return BaseActivity.hasWindowFocus;
	}

	public static Context getContext() {
		return BaseApplication.getContext();
	}

	public static Handler getHandler() {
		return BaseApplication.getHandler();
	}

	public static int getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	// /////////////////获取Activity ///////////////////////////
	public static void putActivity(Activity activity) {
		BaseApplication.putActivity(activity);
	}

	public static void removeActivity(Activity activity) {
		BaseApplication.removeActivity(activity);
	}

	public static Activity getActivity() {
		return BaseApplication.getActivity();
	}

	// /////////////////加载资源文件 ///////////////////////////

	// 获取字符串
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	// 获取字符串数组
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	// 获取图片
	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	// 获取颜色
	public static int getColor(int id) {
		return ContextCompat.getColor(getContext(), id);
	}

	//根据id获取颜色的状态选择器
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	// 获取尺寸
	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
	}

	// /////////////////dip和px转换//////////////////////////

	public static int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static float px2dip(int px) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return px / density;
	}

	// /////////////////获取屏幕高度和宽度//////////////////////////
	public static int getHeightPixels(double d) {
		DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
		int heightPixels = metrics.heightPixels;
		return (int) (heightPixels * d);
	}

	public static int getWidthPixels(double i) {
		DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
		int widthPixels = metrics.widthPixels;
		return (int) (widthPixels * i);
	}

	// /////////////////加载布局文件//////////////////////////
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	// /////////////////判断是否运行在主线程//////////////////////////
	public static boolean isRunOnUIThread() {
		// 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
		int myTid = android.os.Process.myTid();
		if (myTid == getMainThreadId()) {
			return true;
		}

		return false;
	}

	// 运行在主线程
	public static void runOnUIThread(Runnable r) {
		if (isRunOnUIThread()) {
			// 已经是主线程, 直接运行
			r.run();
		} else {
			// 如果是子线程, 借助handler让其运行在主线程
			getHandler().post(r);
		}
	}
	///////////////////////////////////////////////////

	public static void showToast(String s) {
		Toast.makeText(BaseApplication.getContext(), s, Toast.LENGTH_SHORT).show();
	}
}
