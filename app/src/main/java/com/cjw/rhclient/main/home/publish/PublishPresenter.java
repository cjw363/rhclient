package com.cjw.rhclient.main.home.publish;

import android.content.Context;

import javax.inject.Inject;

class PublishPresenter implements PublishContract.Presenter {

	private final PublishContract.View mView;
	private final Context mContext;

	@Inject
	PublishPresenter(Context context, PublishContract.View mHomeView) {
		this.mContext = context;
		this.mView = mHomeView;
	}

}
