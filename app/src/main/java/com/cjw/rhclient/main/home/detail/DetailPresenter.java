package com.cjw.rhclient.main.home.detail;

import android.content.Context;

import com.cjw.rhclient.been.BBs;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.http.HttpResult;
import com.cjw.rhclient.http.HttpResultSubscriber;
import com.cjw.rhclient.http.RxDoOnSubscribe;
import com.cjw.rhclient.http.RxSchedulers;
import com.cjw.rhclient.http.RxTrHttpMethod;
import com.cjw.rhclient.service.RentService;
import com.cjw.rhclient.utils.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

class DetailPresenter implements DetailContract.DetailPresenter {

	private final DetailContract.DetailView mDetailView;
	private final Context mContext;

	@Inject
	DetailPresenter(Context context, DetailContract.DetailView mDetailView) {
		this.mContext = context;
		this.mDetailView = mDetailView;
	}

	@Override
	public void bbs(String input, int rentId) {
		Map<String, String> map = new HashMap<>();
		map.put("token", Session.user.getToken());
		map.put("content", input);
		map.put("rent_id", rentId + "");
		map.put("user_id", Session.user.getId() + "");
		RxTrHttpMethod.getInstance().createService(RentService.class).bbs(map).compose(RxSchedulers.<HttpResult<List<BBs>>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<List<BBs>>(mContext) {
			@Override
			public void _onSuccess(List<BBs> result) {
				mDetailView.showBBsList(result);
				UI.showToast("留言成功");
			}
		});
	}

	@Override
	public void getBBsList(int id) {
		Map<String, String> map = new HashMap<>();
		map.put("token", Session.user.getToken());
		map.put("rent_id", id + "");
		RxTrHttpMethod.getInstance().createService(RentService.class).getBBsList(map).compose(RxSchedulers.<HttpResult<List<BBs>>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<List<BBs>>(mContext) {
			@Override
			public void _onSuccess(List<BBs> result) {
				mDetailView.showBBsList(result);
			}
		});
	}
}
