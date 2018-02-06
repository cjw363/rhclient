package com.cjw.rhclient.main.home.detail;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class DetailPresenterModule {

	private final DetailContract.DetailView mDetailView;
	private final Context mContext;

	public DetailPresenterModule(DetailContract.DetailView view,Context context) {
		mDetailView = view;
		mContext = context;

	}

	@Provides
	DetailContract.DetailView provideDetailContractView(){
		return mDetailView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
