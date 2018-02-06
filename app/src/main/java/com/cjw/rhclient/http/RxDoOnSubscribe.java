package com.cjw.rhclient.http;

import android.content.Context;

import com.cjw.rhclient.view.LoadingDialog;
import com.cjw.rhclient.view.dialog.MyDialogFragment;

import rx.functions.Action0;

public class RxDoOnSubscribe implements Action0 {
	private final Context mContext;

	public RxDoOnSubscribe(Context context) {
		this.mContext = context;
	}

	@Override
	public void call() {
		LoadingDialog.show(mContext);
//		MyDialogFragment.show(mContext);
	}
}
