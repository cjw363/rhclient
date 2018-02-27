package com.cjw.rhclient.main.mine.publish;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class MyPublishPresenterModule {

	private final MyPublishContract.MyPublishView mMyPublishView;
	private final Context mContext;

	public MyPublishPresenterModule(MyPublishContract.MyPublishView view,Context context) {
		mMyPublishView = view;
		mContext = context;

	}

	@Provides
	MyPublishContract.MyPublishView provideMyPublishContractView(){
		return mMyPublishView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
