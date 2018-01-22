package com.cjw.rhclient.main.home.publish;

import dagger.Component;

@Component(modules = PublishPresenterModule.class)
public interface PublishComponent {
	void inject(PublishActivity publishActivity);
}
