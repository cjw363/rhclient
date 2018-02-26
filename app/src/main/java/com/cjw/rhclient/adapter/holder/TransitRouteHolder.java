package com.cjw.rhclient.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseHolder;

import java.util.List;

import butterknife.BindView;

public class TransitRouteHolder extends BaseHolder<List<MassTransitRouteLine.TransitStep>> {
	@BindView(R.id.tv_order)
	TextView mTvOrder;
	@BindView(R.id.tv_content)
	TextView mTvContent;

	public TransitRouteHolder(View itemView) {
		super(itemView);
	}

	@Override
	public void refreshData(List<MassTransitRouteLine.TransitStep> data) {
		mTvContent.setText(data.get(0).getInstructions());
	}
}
