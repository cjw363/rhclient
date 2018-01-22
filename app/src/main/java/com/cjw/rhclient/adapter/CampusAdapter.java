package com.cjw.rhclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.holder.CampusHolder;

import java.util.List;

public class CampusAdapter extends BaseRecyclerViewAdapter<Rent> {

	public CampusAdapter(Context context, List<Rent> data) {
		super(context, data);
	}

	@Override
	public void onLoadMore() {

	}

	@Override
	public View initHolderView(ViewGroup parent, int viewType) {
		return LayoutInflater.from(mContext).inflate(R.layout.layout_campus_item, parent, false);
	}

	@Override
	public BaseHolder<Rent> getHolder(View initHolderView, OnItemClickListener<Rent> itemClickListener, int viewType) {
		return new CampusHolder(initHolderView, itemClickListener);
	}
}
