package com.cjw.rhclient.main.register;

import dagger.Component;

@Component(modules = RegisterPresenterModule.class)
public interface RegisterComponent {
	void inject(RegisterActivity registerActivity);
}
