package com.cjw.rhclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.holder.CampusHolder;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
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

import rx.android.schedulers.AndroidSchedulers;

public class CampusAdapter extends BaseRecyclerViewAdapter<Rent> {
	private static int PAGE_CUR = 0;

	public CampusAdapter(Context context, List<Rent> data) {
		super(context, data);
	}

	@Override
	public void onLoadMore() {
		Map<String, String> map = new HashMap<>();
		map.put("token", Session.user.getToken());
		map.put("type", 5 + "");
		map.put("longitude", Session.location.getLongitude() + "");
		map.put("latitude", Session.location.getLatitude() + "");
		map.put("pageCur", ++PAGE_CUR + "");
		RxTrHttpMethod.getInstance().createService(RentService.class).getCampusList(map).compose(RxSchedulers.<HttpResult<List<Rent>>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<List<Rent>>(mContext) {
			@Override
			public void _onSuccess(List<Rent> result) {
				updateMoreData(result);
			}

			@Override
			public void _onError(Throwable e) {
				PAGE_CUR--;
				super._onError(e);
			}
		});
	}

	@Override
	public View initHolderView(ViewGroup parent, int viewType) {
		return LayoutInflater.from(mContext).inflate(R.layout.layout_campus_item, parent, false);
	}

	@Override
	public BaseHolder<Rent> getHolder(View initHolderView, OnItemClickListener<Rent> itemClickListener, int viewType) {
		return new CampusHolder(initHolderView, itemClickListener);
	}
}
