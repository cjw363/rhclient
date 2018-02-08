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

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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

	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();

	//BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
	//原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
	public void onCreate() {
		mLocationClient = new LocationClient(getApplicationContext());
		//声明LocationClient类
		mLocationClient.registerLocationListener(myListener);
		//注册监听函数
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		//可选，设置定位模式，默认高精度
		//LocationMode.Hight_Accuracy：高精度；
		//LocationMode. Battery_Saving：低功耗；
		//LocationMode. Device_Sensors：仅使用设备；

		option.setCoorType("bd09ll");
		//可选，设置返回经纬度坐标类型，默认gcj02
		//gcj02：国测局坐标；
		//bd09ll：百度经纬度坐标；
		//bd09：百度墨卡托坐标；
		//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

		option.setScanSpan(1000);
		//可选，设置发起定位请求的间隔，int类型，单位ms
		//如果设置为0，则代表单次定位，即仅定位一次，默认为0
		//如果设置非0，需设置1000ms以上才有效

		option.setOpenGps(true);
		//可选，设置是否使用gps，默认false
		//使用高精度和仅用设备两种定位模式的，参数必须设置为true

		option.setLocationNotify(true);
		//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

		option.setIgnoreKillProcess(false);
		//可选，定位SDK内部是一个service，并放到了独立进程。
		//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

		option.SetIgnoreCacheException(false);
		//可选，设置是否收集Crash信息，默认收集，即参数为false

		option.setWifiCacheTimeOut(5 * 60 * 1000);
		//可选，7.2版本新增能力
		//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

		option.setEnableSimulateGps(false);
		//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

		mLocationClient.setLocOption(option);
		//mLocationClient为第二步初始化过的LocationClient对象
		//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
		mLocationClient.start();//；stop()：关闭定位SDK
	}

	public class MyLocationListener extends BDAbstractLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			//此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
			//以下只列举部分获取经纬度相关（常用）的结果信息
			//更多结果信息获取说明，请参照类参考中BDLocation类中的说明

			double latitude = location.getLatitude();    //获取纬度信息
			double longitude = location.getLongitude();    //获取经度信息
			float radius = location.getRadius();    //获取定位精度，默认值为0.0f

			String coorType = location.getCoorType();
			//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

			int errorCode = location.getLocType();
			//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
			System.out.println(latitude+" "+longitude);
			mLocationClient.stop();
		}
	}

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
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
		} else {
			onCreate();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == MY_PERMISSION_REQUEST_CODE) {
		} else {
			mDialog = new ContentDialog.Builder(this).setContent("注意：没有定位权限，部分功能将不可用！请授予权限").isTouchOutCancel(false).isBackCancelable(false).setOkListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					checkLocationPermission();
					mDialog.dismiss();
				}
			}).build();
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
