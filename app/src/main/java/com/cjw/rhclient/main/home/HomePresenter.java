package com.cjw.rhclient.main.home;

import android.content.Context;

import javax.inject.Inject;

class HomePresenter implements HomeContract.HomePresenter {

	private final HomeContract.HomeView mHomeView;
	private final Context mContext;

	@Inject
	HomePresenter(Context context, HomeContract.HomeView mHomeView) {
		this.mContext = context;
		this.mHomeView = mHomeView;
	}

}
