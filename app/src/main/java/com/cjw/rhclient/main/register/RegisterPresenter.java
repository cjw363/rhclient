package com.cjw.rhclient.main.register;

import android.content.Context;

import com.cjw.rhclient.http.HttpResult;
import com.cjw.rhclient.http.HttpResultSubscriber;
import com.cjw.rhclient.http.RxDoOnSubscribe;
import com.cjw.rhclient.http.RxSchedulers;
import com.cjw.rhclient.http.RxTrHttpMethod;
import com.cjw.rhclient.service.SchoolService;
import com.cjw.rhclient.service.UserService;
import com.cjw.rhclient.utils.UI;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

class RegisterPresenter implements RegisterContract.RegisterPresenter {

	private final RegisterContract.RegisterView mRegisterView;
	private Context mContext;

	@Inject
	RegisterPresenter(Context context, RegisterContract.RegisterView mRegisterView) {
		this.mContext = context;
		this.mRegisterView = mRegisterView;
	}

	@Override
	public void register(String name, String password, String school) {
		HashMap<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("password", password);
		map.put("school", school);

		RxTrHttpMethod.getInstance().createService(UserService.class).register(map).compose(RxSchedulers.<HttpResult<String>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<String>() {
			@Override
			public void _onSuccess(String result) {
				UI.showToast("注册成功，请登录");
				mRegisterView.toLogin();
			}
		});
	}

	@Override
	public void getSchoolData() {
		RxTrHttpMethod.getInstance().createService(SchoolService.class).getSchoolData().compose(RxSchedulers.<HttpResult<List<String>>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<List<String>>() {
			@Override
			public void _onSuccess(List<String> result) {
				mRegisterView.setAutoTvData(result);
			}
		});
	}
}
