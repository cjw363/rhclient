package com.cjw.rhclient.main.home.rent;

import android.content.Context;

import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.http.HttpResult;
import com.cjw.rhclient.http.HttpResultSubscriber;
import com.cjw.rhclient.http.RxDoOnSubscribe;
import com.cjw.rhclient.http.RxSchedulers;
import com.cjw.rhclient.http.RxTrHttpMethod;
import com.cjw.rhclient.service.RentService;
import com.cjw.rhclient.utils.UI;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

class RentPresenter implements RentContract.RentPresenter {

	private final RentContract.RentView mRentView;
	private Context mContext;

	@Inject
	RentPresenter(Context context, RentContract.RentView mRentView) {
		this.mContext = context;
		this.mRentView = mRentView;
	}

	@Override
	public void getRentList(int type) {
		RxTrHttpMethod.getInstance().createService(RentService.class).getCampusList(UI.getUser().getToken(), type).compose(RxSchedulers.<HttpResult<List<Rent>>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<List<Rent>>(mContext) {
			@Override
			public void _onSuccess(List<Rent> result) {
				mRentView.showRentList(result);
			}
		});
	}
}
