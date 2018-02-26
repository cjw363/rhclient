package com.cjw.rhclient.main.home.map;

import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public abstract class OnGetMassRoutePlanResultListener implements OnGetRoutePlanResultListener {

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

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
}
