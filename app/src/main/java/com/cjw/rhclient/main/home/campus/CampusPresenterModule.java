package com.cjw.rhclient.main.home.campus;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class CampusPresenterModule {

	private final CampusContract.CampusView mCampusView;
	private final Context mContext;

	public CampusPresenterModule(CampusContract.CampusView view,Context context) {
		mCampusView = view;
		mContext = context;

	}

	@Provides
	CampusContract.CampusView provideCampusContractView(){
		return mCampusView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
