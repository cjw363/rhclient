package com.cjw.rhclient.main.mine.publish;

import android.content.Context;

import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.http.HttpResult;
import com.cjw.rhclient.http.HttpResultSubscriber;
import com.cjw.rhclient.http.RxDoOnSubscribe;
import com.cjw.rhclient.http.RxSchedulers;
import com.cjw.rhclient.http.RxTrHttpMethod;
import com.cjw.rhclient.service.RentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

class MyPublishPresenter implements MyPublishContract.MyPublishPresenter {

	private final MyPublishContract.MyPublishView mMyPublishView;
	private final Context mContext;

	@Inject
	MyPublishPresenter(Context context, MyPublishContract.MyPublishView mMyPublishView) {
		this.mContext = context;
		this.mMyPublishView = mMyPublishView;
	}

	@Override
	public void getMyPublish() {
		Map<String, String> map = new HashMap<>();
		map.put("token", Session.user.getToken());
		map.put("user_id", Session.user.getId() + "");
		RxTrHttpMethod.getInstance().createService(RentService.class).getMyPublishList(map).compose(RxSchedulers.<HttpResult<List<Rent>>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<List<Rent>>(mContext) {
			@Override
			public void _onSuccess(List<Rent> result) {
				mMyPublishView.showMyPublishList(result);
			}
		});
	}

	@Override
	public void deleteRent(int rentId, final int position) {
		Map<String, String> map = new HashMap<>();
		map.put("token", Session.user.getToken());
		map.put("rent_id", rentId + "");
		RxTrHttpMethod.getInstance().createService(RentService.class).deleteRent(map).compose(RxSchedulers.<HttpResult<Void>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<Void>(mContext) {
			@Override
			public void _onSuccess(Void result) {
				mMyPublishView.deleteRentAdapter(position);
			}
		});
	}

	@Override
	public void updateStatusRent(int rentId, final int status, final int position) {
		Map<String, String> map = new HashMap<>();
		map.put("token", Session.user.getToken());
		map.put("rent_id", rentId + "");
		map.put("status", status + "");
		RxTrHttpMethod.getInstance().createService(RentService.class).updateStatusRent(map).compose(RxSchedulers.<HttpResult<Void>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<Void>(mContext) {
			@Override
			public void _onSuccess(Void result) {
				mMyPublishView.updateStatusAdapter(position, status);
			}
		});
	}
}
