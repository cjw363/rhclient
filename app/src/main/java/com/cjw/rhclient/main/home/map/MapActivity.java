package com.cjw.rhclient.main.home.map;

import android.content.Intent;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.utils.mapapi.overlayutil.MassTransitRouteOverlay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseActivity {
	@BindView(R.id.bmapView)
	MapView mMapView;
	@BindView(R.id.bt_nav)
	Button mBtNav;

	private BaiduMap mBaiduMap;
	private LatLng mTargetPoint;
	private LatLng mMinePoint;
	private RoutePlanSearch mRouteSearch;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_map;
	}

	@Override
	protected void initView() {
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		double targetLongitude = intent.getDoubleExtra("target_longitude", 0);
		double targetLatitude = intent.getDoubleExtra("target_latitude", 0);
		double mineLongitude = intent.getDoubleExtra("mine_longitude", 0);
		double mineLatitude = intent.getDoubleExtra("mine_latitude", 0);

		//定义Maker坐标点
		mTargetPoint = new LatLng(targetLatitude, targetLongitude);
		mMinePoint = new LatLng(mineLatitude, mineLongitude);
		createMaker(mTargetPoint);
		createMaker(mMinePoint);

		List<LatLng> points = new ArrayList<>();//可以将多点放到list集合中
		points.add(mMinePoint);//起点坐标
		points.add(mTargetPoint);//终点坐标
		fitMap(points);

	}

	@OnClick(R.id.bt_nav)
	public void onClickView() {
		startTransitRoute(mMinePoint, mTargetPoint);
	}

	//开始路线规划
	private void startTransitRoute(LatLng startPoint, LatLng endPoint) {
		mRouteSearch = RoutePlanSearch.newInstance();
		PlanNode startMassNode = PlanNode.withLocation(startPoint);
		PlanNode endMassNode = PlanNode.withLocation(endPoint);
		mRouteSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

			}

			@Override
			public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
				//获取跨城综合公共交通线路规划结果
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					//未找到结果
					return;
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
					//result.getSuggestAddrInfo()
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					MassTransitRouteLine route = result.getRouteLines().get(0);
					//创建公交路线规划线路覆盖物
					MassTransitRouteOverlay overlay = new MassTransitRouteOverlay(mBaiduMap);
					//设置公交路线规划数据
					overlay.setData(route);
					//将公交路线规划覆盖物添加到地图中
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			}

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

			}

			@Override
			public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

			}

			@Override
			public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

			}
		});
		mRouteSearch.masstransitSearch(new MassTransitRoutePlanOption().from(startMassNode).to(endMassNode));
	}

	private void createMaker(LatLng point) {
		//构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
		//构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
		//在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
	}

	/**
	 * marker都显示在视野
	 *
	 * @param points
	 */
	private void fitMap(List<LatLng> points) {
		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		for (LatLng p : points) {
			builder = builder.include(p);
		}
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
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
