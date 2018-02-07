package com.cjw.rhclient.http;

import android.app.Activity;
import android.content.Context;

import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.view.dialog.LoadingDialog;
import com.cjw.rhclient.view.dialog.TipDialog;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 17-2-23.
 */
public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

	private Context mContext;

	protected HttpResultSubscriber() {}

	protected HttpResultSubscriber(Context context) {
		this.mContext = context;
	}

	@Override
	public void onCompleted() {
		LoadingDialog.close();
	}

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
		//在这里做全局的错误处理
		if (e instanceof HttpException) {
			// ToastUtils.getInstance().showToast(e.getMessage());
		}
		_onError(e);
	}

	@Override
	public void onNext(HttpResult<T> t) {
		if (t.code == 1) {
			_onSuccess(t.result);
		} else {
			_onError(new Throwable("error: " + t.message));
		}
	}

	public abstract void _onSuccess(T t);

	public void _onError(final Throwable e) {
		LoadingDialog.close();
		if (mContext != null) TipDialog.show((Activity) mContext, e.getMessage());
		else UI.showToast(e.getMessage());
		e.printStackTrace();
	}
}