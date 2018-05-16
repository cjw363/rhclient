package com.cjw.rhclient.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.been.Location;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.main.home.publish.PublishActivity;
import com.cjw.rhclient.main.mine.MineFragment;
import com.cjw.rhclient.utils.FragmentFactory;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.view.dialog.ContentDialog;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.drawer)
	DrawerLayout mDrawer;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;
	@BindView(R.id.tv_toolbar_right)
	TextView mTvToolbarRight;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initView() {
		initToolBar();// 初始化toolbar
		initGeoCoder();
		initMine();//初始化我的
	}

	private void initGeoCoder() {
		GeoCoder geoCoder = GeoCoder.newInstance();
		geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					new ContentDialog.Builder(MainActivity.this).setSingleButton().setContent("抱歉，没有检索到该大学，数据库暂时未录入!").build().showDialog();
				} else {
					//获取地理编码结果
					LatLng location = result.getLocation();
					Session.location = new Location(location.latitude, location.longitude, Session.user.getSchoolName());
					initPager();// 初始化radiobutton点击事件和viewpager
				}
			}

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {}
		});
		geoCoder.geocode(new GeoCodeOption().city(Session.user.getProvince()).address(Session.user.getSchoolName()));
	}

	private void initMine() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(R.id.fl_frag_mine, new MineFragment());
		transaction.commit();
	}

	private void initToolBar() {
		//		mToolbar.inflateMenu(R.menu.main);// 设置右上角的填充菜单
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, 0, 0);// 绑定toolbar跟drawerlayout
		toggle.syncState();// .设置菜单切换同步,显示home键
		mDrawer.addDrawerListener(toggle);
		mTvToolbarRight.setText("发布出租");
	}

	private void initPager() {
		mViewpager.setAdapter(new InnerFraPagerAdapter(getSupportFragmentManager()));
		mViewpager.setCurrentItem(0, true);
	}

	public void onClickToolbar(View v) {
		startActivity(new Intent(MainActivity.this, PublishActivity.class));
	}

	private class InnerFraPagerAdapter extends FragmentStatePagerAdapter {

		private String[] tbNames;

		InnerFraPagerAdapter(FragmentManager fm) {
			super(fm);
			tbNames = UI.getStringArray(R.array.toolbar_names);
		}

		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			return tbNames.length;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//super.destroyItem(container, position, object);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
