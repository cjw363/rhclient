package com.cjw.rhclient.main.home.rent;

import dagger.Component;

@Component(modules = RentPresenterModule.class)
public interface RentComponent {
	void inject(RentFragment rentFragment);
}
