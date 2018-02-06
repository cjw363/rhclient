package com.cjw.rhclient.view;

import android.app.ProgressDialog;
import android.content.Context;

import com.cjw.rhclient.utils.UI;

public class LoadingDialog extends ProgressDialog {
	private static LoadingDialog dialog;

	private LoadingDialog(Context context) {
		super(context);
	}

	public static void show(Context context) {
//		if (UI.getHasWindowFocus()) {
			close();
			dialog = new LoadingDialog(context);
			dialog.setMessage("加载中..");
			dialog.show();
//		}
	}

	public static void close() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
}
