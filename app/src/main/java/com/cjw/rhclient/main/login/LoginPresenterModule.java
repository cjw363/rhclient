package com.cjw.rhclient.main.login;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class LoginPresenterModule {

	private final LoginContract.LoginView mLoginView;
	private final Context mContext;

	public LoginPresenterModule(LoginContract.LoginView view,Context context) {
		mLoginView = view;
		mContext = context;

	}

	@Provides
	LoginContract.LoginView provideLoginContractView(){
		return mLoginView;
	}

	@Provides
	Context provideContext(){
		return mContext;
	}
}
