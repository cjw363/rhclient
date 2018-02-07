package com.cjw.rhclient.adapter.holder;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.utils.UrlUtils;

import butterknife.BindView;

public class CampusHolder extends BaseHolder<Rent> {
	@BindView(R.id.aiv_head)
	AppCompatImageView mAivHead;
	@BindView(R.id.tv_distance)
	TextView mTvDistance;
	@BindView(R.id.tv_title)
	TextView mTvTitle;
	@BindView(R.id.tv_house_type)
	TextView mTvHouseType;
	@BindView(R.id.tv_location)
	TextView mTvLocation;
	@BindView(R.id.tv_label1)
	TextView mTvLabel1;
	@BindView(R.id.tv_label2)
	TextView mTvLabel2;
	@BindView(R.id.tv_label3)
	TextView mTvLabel3;
	@BindView(R.id.tv_amount)
	TextView mTvAmount;

	public CampusHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<Rent> itemClickListener) {
		super(itemView, itemClickListener);
	}

	@Override
	public void refreshData(Rent data) {
		mTvTitle.setText(data.getTitle());
		mTvHouseType.setText(data.getHouseType());
		mTvLocation.setText(data.getLocation());
		mTvLabel1.setText(data.getLabel());
		mTvAmount.setText("￥" + data.getAmount() + "/月");
		Glide.with(UI.getContext()).load(UrlUtils.getImageUrl(data.getTitleImg())).into(mAivHead);
	}

}
