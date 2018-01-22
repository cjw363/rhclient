package com.cjw.rhclient.main.login;

import dagger.Component;

@Component(modules = LoginPresenterModule.class)
public interface LoginComponent {
	void inject(LoginActivity loginActivity);
}
