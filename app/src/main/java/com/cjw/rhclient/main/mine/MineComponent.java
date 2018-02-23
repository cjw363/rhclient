package com.cjw.rhclient.main.mine;

import com.cjw.rhclient.main.home.HomeFragment;

import dagger.Component;

@Component(modules = MinePresenterModule.class)
public interface MineComponent {
	void inject(MineFragment mineFragment);
}
