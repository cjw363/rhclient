package com.cjw.rhclient.main.login;

import android.content.Context;

import com.cjw.rhclient.been.User;
import com.cjw.rhclient.http.HttpResult;
import com.cjw.rhclient.http.HttpResultSubscriber;
import com.cjw.rhclient.http.RxDoOnSubscribe;
import com.cjw.rhclient.http.RxSchedulers;
import com.cjw.rhclient.http.RxTrHttpMethod;
import com.cjw.rhclient.service.UserService;
import com.cjw.rhclient.utils.UI;

import java.util.HashMap;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

class LoginPresenter implements LoginContract.LoginPresenter {

	private final LoginContract.LoginView mLoginView;
	private Context mContext;

	@Inject
	LoginPresenter(Context context, LoginContract.LoginView mLoginView) {
		this.mContext = context;
		this.mLoginView = mLoginView;
	}

	@Override
	public void login(String name, String password) {
		HashMap<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("password", password);

		RxTrHttpMethod.getInstance().createService(UserService.class).login(map).compose(RxSchedulers.<HttpResult<User>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<User>(mContext) {
			@Override
			public void _onSuccess(User result) {
				UI.showToast("登录成功");
				mLoginView.toHome(result);
			}
		});
	}
}
