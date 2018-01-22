package com.cjw.rhclient.main.register;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class RegisterPresenterModule {

	private final RegisterContract.RegisterView mRegisterView;
	private final Context mContext;

	public RegisterPresenterModule(RegisterContract.RegisterView view,Context context) {
		mRegisterView = view;
		mContext = context;

	}

	@Provides
	RegisterContract.RegisterView provideLoginContractView(){
		return mRegisterView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
