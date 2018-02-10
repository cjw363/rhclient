package com.cjw.rhclient.main.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseFragment;
import com.cjw.rhclient.utils.FragmentFactory;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.view.PagerTab;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeContract.HomeView {
	@BindView(R.id.pagertab)
	PagerTab mPagerTab;
	@BindView(R.id.viewpager)
	ViewPager mViewPager;

	@Override
	public int getContentLayoutId() {
		return R.layout.fragment_home;
	}

	@Override
	protected void initView() {
		DaggerHomeComponent.builder().homePresenterModule(new HomePresenterModule(this, getActivity())).build().inject(this);
		initPager();// 初始化pagertab和viewpager
	}

	private void initPager() {
		mViewPager.setAdapter(new InnerFraPagerAdapter(getChildFragmentManager()));
		mViewPager.setOffscreenPageLimit(1);// 缓存当前界面每一侧的界面数
		mPagerTab.setViewPager(mViewPager);
		mViewPager.setCurrentItem(0);
	}

	private class InnerFraPagerAdapter extends FragmentStatePagerAdapter {

		private String[] tabNames;

		InnerFraPagerAdapter(FragmentManager fm) {
			super(fm);
			tabNames = UI.getStringArray(R.array.tab_names);
		}

		@Override
		public Fragment getItem(int position) {
			BaseFragment fragment = FragmentFactory.createTabFragment(position);
			Bundle bundle = new Bundle();
			bundle.putInt("type", position);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabNames[position];
		}

		@Override
		public int getCount() {
			return tabNames.length;
		}

	}
}
