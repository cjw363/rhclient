package com.cjw.rhclient.main.mine.publish;

import com.cjw.rhclient.main.home.HomeFragment;

import dagger.Component;

@Component(modules = MyPublishPresenterModule.class)
public interface MyPublishComponent {
	void inject(MyPublishActivity myPublishActivity);
}
