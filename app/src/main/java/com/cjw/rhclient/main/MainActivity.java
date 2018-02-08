package com.cjw.rhclient.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.cjw.rhclient.been.User;
import com.cjw.rhclient.main.home.publish.PublishActivity;
import com.cjw.rhclient.utils.FragmentFactory;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.view.dialog.BaseCustomDialog;
import com.cjw.rhclient.view.dialog.ContentDialog;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
	private static final int MY_PERMISSION_REQUEST_CODE = 6;

	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.drawer)
	DrawerLayout mDrawer;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;
	@BindView(R.id.tv_toolbar_right)
	TextView mTvToolbarRight;
	private BaseCustomDialog mDialog;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initView() {
		initToolBar();// 初始化toolbar
		checkLocationPermission();
		initPager();// 初始化radiobutton点击事件和viewpager
	}

	private void checkLocationPermission() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
		  .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
		} else {
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == MY_PERMISSION_REQUEST_CODE) {
		} else {
			mDialog = new ContentDialog.Builder(this).setContent("注意：没有定位权限，部分功能将不可用！请授予权限")
			  .isTouchOutCancel(false)
			  .isBackCancelable(false)
			  .setOkListener(new View.OnClickListener() {
				  @Override
				  public void onClick(View view) {
					  checkLocationPermission();
					  mDialog.dismiss();
				  }
			  })
			  .build();
			mDialog.showDialog();
		}
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		User user = (User) intent.getSerializableExtra("user");
		UI.setUser(user);
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
