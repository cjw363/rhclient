package com.cjw.rhclient.http;

import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.view.LoadingDialog;
import com.cjw.rhclient.view.dialog.MyDialogFragment;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 17-2-23.
 */
public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

	@Override
	public void onCompleted() {
//		LoadingDialog.close();
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
//		LoadingDialog.close();
		UI.showToast(e.getMessage());
		e.printStackTrace();
	}
}