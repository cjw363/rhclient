package com.cjw.rhclient.main.login;

import com.cjw.rhclient.been.User;

interface LoginContract {

	interface LoginView {
		void toHome(User result);
	}

	interface LoginPresenter {
		void login(String name, String password);
	}
}
