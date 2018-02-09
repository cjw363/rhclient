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
	@BindView(R.id.aiv_pic1)
	AppCompatImageView mAivPic1;
	@BindView(R.id.aiv_pic2)
	AppCompatImageView mAivPic2;
	@BindView(R.id.aiv_pic3)
	AppCompatImageView mAivPic3;
	@BindView(R.id.tv_distance)
	TextView mTvDistance;
	@BindView(R.id.tv_location)
	TextView mTvLocation;
	@BindView(R.id.tv_amount)
	TextView mTvAmount;

	public CampusHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<Rent> itemClickListener) {
		super(itemView, itemClickListener);
	}

	@Override
	public void refreshData(Rent data) {
		mTvLocation.setText(data.getLocation());
		mTvAmount.setText("￥" + data.getAmount() + "/月");
		Glide.with(UI.getContext()).load(UrlUtils.getImageUrl(data.getTitleImg())).into(mAivPic1);
	}

}
