package com.cjw.rhclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.holder.TransitRouteHolder;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;

import java.util.List;

public class TransitRouteAdapter extends BaseRecyclerViewAdapter<List<MassTransitRouteLine.TransitStep>> {
	public TransitRouteAdapter(Context context, List<List<MassTransitRouteLine.TransitStep>> data) {
		super(context, data);
	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public boolean isHasMore() {
		return false;
	}

	@Override
	public View initHolderView(ViewGroup parent, int viewType) {
		return LayoutInflater.from(mContext).inflate(R.layout.layout_transit_route_item, parent, false);
	}

	@Override
	public BaseHolder<List<MassTransitRouteLine.TransitStep>> getHolder(View initHolderView, OnItemClickListener<List<MassTransitRouteLine.TransitStep>> itemClickListener, int viewType) {
		return new TransitRouteHolder(initHolderView);
	}
}
