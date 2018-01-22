package com.cjw.rhclient.main.home.campus;

import dagger.Component;

@Component(modules = CampusPresenterModule.class)
public interface CampusComponent {
	void inject(CampusFragment CampusFragment);
}
