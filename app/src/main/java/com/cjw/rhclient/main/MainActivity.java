package com.cjw.rhclient.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.been.User;
import com.cjw.rhclient.main.home.publish.PublishActivity;
import com.cjw.rhclient.utils.FragmentFactory;
import com.cjw.rhclient.utils.UI;

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
		initPager();// 初始化radiobutton点击事件和viewpager
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		Session.user = (User) intent.getSerializableExtra("user");
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
