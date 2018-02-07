package com.cjw.rhclient.main.home.rent;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class RentPresenterModule {

	private final RentContract.RentView mRentView;
	private final Context mContext;

	public RentPresenterModule(RentContract.RentView view,Context context) {
		mRentView = view;
		mContext = context;

	}

	@Provides
	RentContract.RentView provideRentContractView(){
		return mRentView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
