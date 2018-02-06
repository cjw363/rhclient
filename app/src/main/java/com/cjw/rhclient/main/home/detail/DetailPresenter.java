package com.cjw.rhclient.main.home.detail;

import android.content.Context;

import javax.inject.Inject;

class DetailPresenter implements DetailContract.DetailPresenter {

	private final DetailContract.DetailView mDetailView;
	private final Context mContext;

	@Inject
	DetailPresenter(Context context, DetailContract.DetailView mDetailView) {
		this.mContext = context;
		this.mDetailView = mDetailView;
	}

}
