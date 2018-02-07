package com.cjw.rhclient.http;

import android.app.Activity;
import android.content.Context;

import com.cjw.rhclient.view.dialog.LoadingDialog;

import rx.functions.Action0;

public class RxDoOnSubscribe implements Action0 {
	private boolean mIsShowDialog = true;
	private Context mContext;

	public RxDoOnSubscribe(Context context) {
		this.mContext = context;
	}

	public RxDoOnSubscribe(Context context, boolean isShowDialog) {
		this.mContext = context;
		this.mIsShowDialog = isShowDialog;
	}

	@Override
	public void call() {
		if (mIsShowDialog) LoadingDialog.show((Activity) mContext);
	}
}
