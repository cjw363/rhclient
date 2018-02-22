package com.cjw.rhclient.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.been.BBs;

import butterknife.BindView;

public class BBsHolder extends BaseHolder<BBs> {
	@BindView(R.id.tv_name)
	TextView mTvName;
	@BindView(R.id.tv_bbs)
	TextView mTvBbs;

	public BBsHolder(View itemView) {super(itemView);}

	@Override
	public void refreshData(BBs data) {
		mTvName.setText(data.getName());
		mTvBbs.setText(data.getContent());
	}
}
