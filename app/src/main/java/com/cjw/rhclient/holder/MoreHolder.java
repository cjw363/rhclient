package com.cjw.rhclient.holder;

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

	private LinearLayout ll_load_more;
	private TextView tv_more_fail;

	public MoreHolder(View itemView, boolean hasMore) {
		super(itemView);
		ll_load_more = (LinearLayout) itemView.findViewById(R.id.ll_load_more);
		tv_more_fail = (TextView) itemView.findViewById(R.id.tv_more_fail);

		loadData(hasMore ? STATE_MORE : STATE_NONE);
	}

	@Override
	public void refreshData(Integer itemData) {
		switch (itemData) {
			case STATE_MORE:
				ll_load_more.setVisibility(View.VISIBLE);
				tv_more_fail.setVisibility(View.GONE);
				break;
			case STATE_NONE:
				ll_load_more.setVisibility(View.GONE);
				tv_more_fail.setVisibility(View.GONE);
				break;
			case STATE_FAIL:
				ll_load_more.setVisibility(View.GONE);
				tv_more_fail.setVisibility(View.VISIBLE);
				break;
		}
	}
}
