package com.cjw.rhclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.holder.BBsHolder;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
import com.cjw.rhclient.been.BBs;

import java.util.List;

public class BBsAdapter extends BaseRecyclerViewAdapter<BBs> {

	public BBsAdapter(Context context, List<BBs> data) {
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
		return LayoutInflater.from(mContext).inflate(R.layout.layout_bbs_item, parent, false);
	}

	@Override
	public BaseHolder<BBs> getHolder(View initHolderView, OnItemClickListener<BBs> itemClickListener, int viewType) {
		return new BBsHolder(initHolderView);
	}

}
