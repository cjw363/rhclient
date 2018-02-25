package com.cjw.rhclient.view.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.cjw.rhclient.utils.LogUtils;

import java.lang.ref.WeakReference;

public class LoadingDialog {
	private static WeakReference<Activity> mWeakReference;
	private static ProgressDialog mProgressDialog;

	public static void show(Activity activity) {
		show(activity, "加载中...");
	}

	public static void show(Activity activity, String message) {
		show(activity, message, true);
	}

	/**
	 * @param activity 需要弹窗的activity
	 * @param message  弹窗展示的内容
	 * @param flag     触摸弹窗外区域，是否取消窗口
	 */
	public static void show(Activity activity, String message, boolean flag) {
		if (!isLiving(activity)) {
			return;
		}
		if (mWeakReference == null) {
			mWeakReference = new WeakReference<>(activity);
		}
		activity = mWeakReference.get();

		if (mProgressDialog == null) {
			if (activity.getParent() != null) {
				mProgressDialog = new ProgressDialog(activity.getParent());
			} else {
				mProgressDialog = new ProgressDialog(activity);
			}
		}
		mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mProgressDialog = null;
				mWeakReference = null;
			}
		});
		//		if (!mProgressDialog.isShowing()) {
		//			mProgressDialog.dismiss();
		mProgressDialog.setMessage(message);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCancelable(flag);
		mProgressDialog.show();
		//		} else {
		//			mProgressDialog.setMessage(message);
		//		}
	}

	/**
	 * 判断activity是否存活
	 */
	private static boolean isLiving(Activity activity) {
		if (activity == null) {
			LogUtils.d("activity == null");
			return false;
		}
		if (activity.isFinishing()) {
			LogUtils.d("activity is finishing");
			return false;
		}
		return true;
	}

	/**
	 * 关闭进度框
	 */
	public static void close() {
		if (isShowing(mProgressDialog)) {//&& isExistLiving(mWeakReference)
			mProgressDialog.dismiss();
			mProgressDialog = null;
			mWeakReference.clear();
			mWeakReference = null;
		}
	}

	/**
	 * 判断进度框是否正在显示
	 */
	private static boolean isShowing(ProgressDialog dialog) {
		return dialog != null && dialog.isShowing();
	}

	private static boolean isExistLiving(WeakReference<Activity> weakReference) {
		if (weakReference != null) {
			Activity activity = weakReference.get();
			if (activity == null) {
				return false;
			}
			if (activity.isFinishing()) {
				return false;
			}
			return true;
		}
		return false;
	}
}
