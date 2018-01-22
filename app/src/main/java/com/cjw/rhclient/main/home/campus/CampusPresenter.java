package com.cjw.rhclient.main.home.campus;

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

class CampusPresenter implements CampusContract.CampusPresenter {

	private final CampusContract.CampusView mCampusView;
	private Context mContext;

	@Inject
	CampusPresenter(Context context, CampusContract.CampusView mCampusView) {
		this.mContext = context;
		this.mCampusView = mCampusView;
	}

	@Override
	public void getCampusList() {
		RxTrHttpMethod.getInstance().createService(RentService.class).getCampusList(UI.getUser().getToken()).compose(RxSchedulers.<HttpResult<List<Rent>>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<List<Rent>>() {
			@Override
			public void _onSuccess(List<Rent> result) {
				mCampusView.showCampusList(result);
			}
		});
	}
}
