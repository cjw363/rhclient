package com.cjw.rhclient.main.mine;

import android.content.Context;

import com.cjw.rhclient.been.Session;
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

class MinePresenter implements MineContract.MinePresenter {

	private final MineContract.MineView mMineView;
	private final Context mContext;

	@Inject
	MinePresenter(Context context, MineContract.MineView mMineView) {
		this.mContext = context;
		this.mMineView = mMineView;
	}

	@Override
	public void outLogin() {
		HashMap<String, String> map = new HashMap<>();
		map.put("token", Session.user.getToken());
		RxTrHttpMethod.getInstance().createService(UserService.class).outLogin(map).compose(RxSchedulers.<HttpResult<Void>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<Void>(mContext) {
			@Override
			public void _onSuccess(Void result) {
				UI.showToast("注销成功");
				mMineView.toLogin();
			}
		});
	}
}
