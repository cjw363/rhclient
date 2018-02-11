package com.cjw.rhclient.main.home.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.utils.LogUtils;
import com.cjw.rhclient.view.dialog.BaseCustomDialog;
import com.cjw.rhclient.view.dialog.ContentDialog;

public class MapActivity extends BaseActivity {
	private static final int MY_PERMISSION_REQUEST_CODE = 6;

	private BaseCustomDialog mDialog;
	private MapView mMapView = null;
	private BaiduMap mBaiduMap;
	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();
	private GeoCoder mGeoCoder;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_map;
	}

	@Override
	protected void initView() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		checkLocationPermission();
	}

	private void checkLocationPermission() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
		  .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
		} else {
			startLocation();
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

	//BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
	//原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
	public void startLocation() {
		mLocationClient = new LocationClient(getApplicationContext());
		//声明LocationClient类
		mLocationClient.registerLocationListener(myListener);
		//注册监听函数
		mLocationClient.setLocOption(BaiduMapHelper.getLocationOption());
		//mLocationClient为第二步初始化过的LocationClient对象
		//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
		mLocationClient.start();//；stop()：关闭定位SDK

		mGeoCoder = GeoCoder.newInstance();
		mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					//没有检索到结果
					System.out.println("null");
				} else {
					//获取地理编码结果
					LatLng location = result.getLocation();
					System.out.println(location.latitude + " " + location.longitude);
				}

			}

			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					//没有找到检索结果
					System.out.println("null");
				} else {
					//获取反向地理编码结果
					LatLng location = result.getLocation();
					System.out.println(location.latitude + " " + location.longitude);
				}
			}
		});

	}

	private class MyLocationListener extends BDAbstractLocationListener {
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

			String addr = location.getAddrStr();    //获取详细地址信息
			String country = location.getCountry();    //获取国家
			String province = location.getProvince();    //获取省份
			String city = location.getCity();    //获取城市
			String district = location.getDistrict();    //获取区县
			String street = location.getStreet();    //获取街道信息
			LogUtils.d(latitude + " " + longitude);
			mLocationClient.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
		mGeoCoder.geocode(new GeoCodeOption().city("广州").address("仲恺农业工程学院白云区"));
	}

	@Override
	protected void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
}
