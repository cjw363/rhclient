package com.cjw.rhclient.main.home.detail;

import com.cjw.rhclient.main.home.HomeFragment;

import dagger.Component;

@Component(modules = DetailPresenterModule.class)
public interface DetailComponent {
	void inject(DetailActivity detailActivity);
}
