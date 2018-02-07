package com.cjw.rhclient.view.dialog;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class TipDialog {
	private static WeakReference<Activity> mWeakReference;
	private static BaseCustomDialog mDialog;


	/**
	 * @param activity 需要弹窗的activity
	 * @param message  弹窗展示的内容
	 */
	public static void show(Activity activity, String message) {
		if (!isLiving(activity)) {
			return;
		}
		if (mWeakReference == null) {
			mWeakReference = new WeakReference<>(activity);
		}
		activity = mWeakReference.get();

		if (mDialog == null) {
			if (activity.getParent() != null) {
				mDialog = new ContentDialog.Builder(activity.getParent()).setContent(message)
				  .setSingleButton()
				  .build();
			} else {
				mDialog = new ContentDialog.Builder(activity).setContent(message)
				  .setSingleButton()
				  .build();
			}
		}
		mDialog.showDialog();
	}

	/**
	 * 判断activity是否存活
	 */
	private static boolean isLiving(Activity activity) {
		if (activity == null) {
			return false;
		}
		if (activity.isFinishing()) {
			return false;
		}
		return true;
	}

	/**
	 * 关闭进度框
	 */
	public static void close() {
		if (isShowing(mDialog)) {//&& isExistLiving(mWeakReference)
			mDialog.dismiss();
			mDialog = null;
			mWeakReference.clear();
			mWeakReference = null;
		}
	}

	/**
	 * 判断进度框是否正在显示
	 */
	private static boolean isShowing(BaseCustomDialog dialog) {
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
