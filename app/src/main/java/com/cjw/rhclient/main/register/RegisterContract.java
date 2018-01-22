package com.cjw.rhclient.main.register;

import java.util.List;

interface RegisterContract {

	interface RegisterView {
		void toLogin();

		void setAutoTvData(List<String> result);
	}

	interface RegisterPresenter {
		void register(String name, String password, String school);

		void getSchoolData();
	}
}
