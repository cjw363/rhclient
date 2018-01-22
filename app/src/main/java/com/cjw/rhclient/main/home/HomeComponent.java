package com.cjw.rhclient.main.home;

import dagger.Component;

@Component(modules = HomePresenterModule.class)
public interface HomeComponent {
	void inject(HomeFragment homeFragment);
}
