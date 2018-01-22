package com.cjw.rhclient.main.home.publish;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class PublishPresenterModule {

	private final PublishContract.View mView;
	private final Context mContext;

	public PublishPresenterModule(PublishContract.View view,Context context) {
		mView = view;
		mContext = context;

	}

	@Provides
	PublishContract.View providePublishContractView(){
		return mView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
