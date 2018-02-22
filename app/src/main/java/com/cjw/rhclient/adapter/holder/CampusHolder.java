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
import com.cjw.rhclient.view.ShapeImageView;

import butterknife.BindView;

public class CampusHolder extends BaseHolder<Rent> {
	@BindView(R.id.aiv_head)
	ShapeImageView mAivHead;
	@BindView(R.id.aiv_pic)
	AppCompatImageView mAivPic;
	@BindView(R.id.tv_distance)
	TextView mTvDistance;
	@BindView(R.id.tv_name)
	TextView mTvName;
	@BindView(R.id.tv_location)
	TextView mTvLocation;
	@BindView(R.id.tv_amount)
	TextView mTvAmount;
	@BindView(R.id.tv_content)
	TextView mTvContent;

	public CampusHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<Rent> itemClickListener) {
		super(itemView, itemClickListener);
	}

	@Override
	public void refreshData(Rent data) {
		mTvName.setText(data.getTitle());
		mTvLocation.setText(data.getLocation());
		mTvContent.setText(data.getContent());
		mTvAmount.setText("￥" + data.getAmount() + "/月");
		Glide.with(UI.getContext()).load(UrlUtils.getImageUrl(data.getTitleImg())).into(mAivPic);
	}

}
