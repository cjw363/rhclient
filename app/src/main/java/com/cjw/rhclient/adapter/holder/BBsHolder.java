package com.cjw.rhclient.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.been.BBs;
import com.cjw.rhclient.utils.DateUtil;

import butterknife.BindView;

public class BBsHolder extends BaseHolder<BBs> {
	@BindView(R.id.tv_name)
	TextView mTvName;
	@BindView(R.id.tv_bbs)
	TextView mTvBbs;
	@BindView(R.id.tv_time)
	TextView mTvTime;

	public BBsHolder(View itemView) {super(itemView);}

	@Override
	public void refreshData(BBs data) {
		mTvName.setText(data.getName());
		mTvBbs.setText(data.getContent());
		mTvTime.setText(DateUtil.stampToDate(data.getTime()));
	}
}
