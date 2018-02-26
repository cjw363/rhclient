package com.cjw.rhclient.main.home.map;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.TransitRouteAdapter;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.utils.DrawableUtils;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.utils.mapapi.overlayutil.MassTransitRouteOverlay;
import com.cjw.rhclient.view.dialog.ContentDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MapActivity extends BaseActivity {
	public static final String ACTION_MAP_NAVIGATION = "action_map_navigation";
	public static final String ACTION_MAP_LOCATION = "action_map_location";

	@BindView(R.id.bmapView)
	MapView mMapView;
	@BindView(R.id.bt_nav)
	Button mBtNav;
	@BindView(R.id.aiv_arrow)
	AppCompatImageView mAivArrow;
	@BindView(R.id.rl_transit_route)
	RelativeLayout mRlTransitRoute;
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.ll_transit_route)
	LinearLayout mLlTransitRoute;
	@BindView(R.id.tv_title)
	TextView mTvTitle;
	@BindView(R.id.tv_tips)
	TextView mTvTips;

	private BaiduMap mBaiduMap;
	private LatLng mTargetPoint;
	private LatLng mMinePoint;
	private RoutePlanSearch mRouteSearch;
	private LinearLayout.LayoutParams mParams;
	private int mHeight;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_map;
	}

	@Override
	protected void initView() {
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));

		mRlTransitRoute.setTag(true);//默认打开
		mParams = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
		mHeight = mParams.height;
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		String action = intent.getAction();
		if (ACTION_MAP_NAVIGATION.equals(action)) {
			actionNavigation(intent);
		} else if (ACTION_MAP_LOCATION.equals(action)) {
			actionLocation();
		}
	}

	private void actionLocation() {
		mBtNav.setVisibility(View.GONE);
		MapActivityPermissionsDispatcher.startLocationWithCheck(this);
	}

	private void actionNavigation(Intent intent) {
		mBtNav.setVisibility(View.VISIBLE);
		double targetLongitude = intent.getDoubleExtra("target_longitude", 0);
		double targetLatitude = intent.getDoubleExtra("target_latitude", 0);
		double mineLongitude = intent.getDoubleExtra("mine_longitude", 0);
		double mineLatitude = intent.getDoubleExtra("mine_latitude", 0);

		//定义Maker坐标点
		mTargetPoint = new LatLng(targetLatitude, targetLongitude);
		mMinePoint = new LatLng(mineLatitude, mineLongitude);
		createMaker(mTargetPoint, R.drawable.ic_house);
		createMaker(mMinePoint, R.drawable.ic_marker);

		List<LatLng> points = new ArrayList<>();//可以将多点放到list集合中
		//可以将多点放到list集合中
		points.add(mMinePoint);//起点坐标
		points.add(mTargetPoint);//终点坐标
		fitMap(points);
	}

	@OnClick({R.id.bt_nav, R.id.rl_transit_route})
	public void onClickView(View v) {
		switch (v.getId()) {
			case R.id.bt_nav:
				startTransitRoute(mMinePoint, mTargetPoint);
				break;
			case R.id.rl_transit_route:
				boolean isOpen = (Boolean) mRlTransitRoute.getTag();
				ValueAnimator animator;
				if (isOpen) {
					mTvTips.setText("(点击展开)");
					mAivArrow.setBackgroundResource(R.mipmap.arrow_down);
					animator = ValueAnimator.ofInt(mHeight, 0);
				} else {
					mTvTips.setText("(点击收起)");
					mAivArrow.setBackgroundResource(R.mipmap.arrow_up);
					animator = ValueAnimator.ofInt(0, mHeight);
				}
				mRlTransitRoute.setTag(!isOpen);
				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animator) {
						mParams.height = (Integer) animator.getAnimatedValue();
						mRecyclerView.setLayoutParams(mParams);
					}
				});
				animator.setDuration(300);
				animator.start();
				break;
		}
	}

	//开始路线规划
	private void startTransitRoute(LatLng startPoint, LatLng endPoint) {
		mRouteSearch = RoutePlanSearch.newInstance();
		PlanNode startMassNode = PlanNode.withLocation(startPoint);
		PlanNode endMassNode = PlanNode.withLocation(endPoint);
		mRouteSearch.setOnGetRoutePlanResultListener(new OnGetMassRoutePlanResultListener() {
			@Override
			public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
				//获取跨城综合公共交通线路规划结果
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					new ContentDialog.Builder(MapActivity.this).setSingleButton().setContent("未找到结果!").build().showDialog();
					return;
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
					//result.getSuggestAddrInfo()
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					MassTransitRouteLine route = result.getRouteLines().get(0);
					//展示路线面板
					showTransitRoutePanel(route);
					//创建公交路线规划线路覆盖物
					MassTransitRouteOverlay overlay = new MassTransitRouteOverlay(mBaiduMap);
					//设置公交路线规划数据
					overlay.setData(route);
					//将公交路线规划覆盖物添加到地图中
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			}
		});
		mRouteSearch.masstransitSearch(new MassTransitRoutePlanOption().from(startMassNode).to(endMassNode));
	}

	//展示路线面板
	private void showTransitRoutePanel(MassTransitRouteLine route) {
		if (route != null) {
			mLlTransitRoute.setVisibility(View.VISIBLE);

			mTvTitle.setText("总路程:" + route.getDistance() + "m");

			LinearLayoutManager layoutManager = new LinearLayoutManager(UI.getContext());
			layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
			mRecyclerView.setLayoutManager(layoutManager);
			mRecyclerView.setAdapter(new TransitRouteAdapter(this, route.getNewSteps()));
		} else {
			mLlTransitRoute.setVisibility(View.GONE);
		}
	}

	private void createMaker(LatLng point, int iconResource) {
		//构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(DrawableUtils.getBitmapFromDrawable(this, iconResource));
		//构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
		//在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
	}

	//marker都显示在视野
	private void fitMap(final List<LatLng> points) {
		mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
			@Override
			public void onMapLoaded() {
				LatLngBounds.Builder builder = new LatLngBounds.Builder();
				for (LatLng p : points) {
					builder = builder.include(p);
				}
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
			}
		});
	}

	@NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
	public void startLocation() {
		BaiduMapHelper.startLocation(getApplicationContext(), new BDAbstractLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				// 开启定位图层
				mBaiduMap.setMyLocationEnabled(true);

				// 构造定位数据
				MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
				  // 此处设置开发者获取到的方向信息，顺时针0-360
				  .direction(0).latitude(location.getLatitude()).longitude(location.getLongitude()).build();

				// 设置定位数据
				mBaiduMap.setMyLocationData(locData);
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(update);
			}
		});
	}

	@OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
	public void onPermissionDenied() {
		UI.showToast("定位功能需要必要权限!");
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		MapActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		if (mRouteSearch != null) mRouteSearch.destroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
}
