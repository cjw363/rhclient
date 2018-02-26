package com.cjw.rhclient.adapter.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseHolder;

public class MoreHolder extends BaseHolder<Integer> {

	// 三种状态
	// 1.加载更多
	// 2.没有加载更多
	// 3.加载失败
	public static final int STATE_MORE = 0;
	public static final int STATE_NONE = 1;
	public static final int STATE_FAIL = 2;

	private LinearLayout mLlLoadMore;
	private TextView mTvMoreFail;

	public MoreHolder(View itemView, boolean hasMore) {
		super(itemView);
		mLlLoadMore = (LinearLayout) itemView.findViewById(R.id.ll_load_more);
		mTvMoreFail = (TextView) itemView.findViewById(R.id.tv_more_fail);

		loadData(hasMore ? STATE_MORE : STATE_NONE);
	}

	@Override
	public void refreshData(Integer itemData) {
		switch (itemData) {
			case STATE_MORE:
				mLlLoadMore.setVisibility(View.VISIBLE);
				mTvMoreFail.setVisibility(View.GONE);
				break;
			case STATE_NONE:
				mLlLoadMore.setVisibility(View.GONE);
				mTvMoreFail.setVisibility(View.GONE);
				break;
			case STATE_FAIL:
				mLlLoadMore.setVisibility(View.GONE);
				mTvMoreFail.setVisibility(View.VISIBLE);
				break;
		}
	}
}
