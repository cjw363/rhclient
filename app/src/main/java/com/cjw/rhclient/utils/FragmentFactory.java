package com.cjw.rhclient.utils;

import android.os.Bundle;

import com.cjw.rhclient.base.BaseFragment;
import com.cjw.rhclient.main.home.HomeFragment;
import com.cjw.rhclient.main.home.campus.CampusFragment;
import com.cjw.rhclient.main.home.rent.RentFragment;

import java.util.concurrent.ConcurrentHashMap;

public class FragmentFactory {
	//线程安全的hashmap
	private static ConcurrentHashMap<Integer, BaseFragment> fragMap = new ConcurrentHashMap<Integer, BaseFragment>();
	private static ConcurrentHashMap<Integer, BaseFragment> tabFragMap = new ConcurrentHashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int position) {
		BaseFragment fragment = fragMap.get(position);
		if (fragment == null) {
			switch (position) {
				case 0:
					fragment = new HomeFragment();
					break;
			}
			assert fragment != null;
			fragMap.put(position, fragment);
		}
		return fragment;
	}

	public static BaseFragment createTabFragment(int position) {
		BaseFragment fragment = tabFragMap.get(position);
		if (fragment == null) {
			switch (position) {
				case 0:
					fragment = new CampusFragment();
					break;
				case 1:
					fragment = new RentFragment();
					break;
				case 2:
					fragment = new RentFragment();
					break;
				case 3:
					fragment = new RentFragment();
					break;
				case 4:
					fragment = new RentFragment();
					break;
			}
			assert fragment != null;
			Bundle bundle = new Bundle();
			bundle.putInt("type", position + 1);
			fragment.setArguments(bundle);
			tabFragMap.put(position, fragment);
		}
		return fragment;
	}
}
