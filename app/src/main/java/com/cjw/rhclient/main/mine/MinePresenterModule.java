package com.cjw.rhclient.main.mine;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class MinePresenterModule {

	private final MineContract.MineView mMineView;
	private final Context mContext;

	public MinePresenterModule(MineContract.MineView view,Context context) {
		mMineView = view;
		mContext = context;

	}

	@Provides
	MineContract.MineView provideMineContractView(){
		return mMineView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
