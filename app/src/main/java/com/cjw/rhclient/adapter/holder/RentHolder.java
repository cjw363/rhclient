package com.cjw.rhclient.adapter.holder;

import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseHolder;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.been.common.Common;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.utils.UrlUtils;

import butterknife.BindView;

import static com.cjw.rhclient.R.id.tv_label2;

public class RentHolder extends BaseHolder<Rent> {
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
	@BindView(tv_label2)
	TextView mTvLabel2;
	@BindView(R.id.tv_label3)
	TextView mTvLabel3;
	@BindView(R.id.tv_amount)
	TextView mTvAmount;
	@BindView(R.id.rl_rent_status)
	RelativeLayout mRlRentStatus;
	@BindView(R.id.tv_rent_status)
	TextView mTvRentStatus;

	public RentHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener<Rent> itemClickListener) {
		super(itemView, itemClickListener);
	}

	@Override
	public void refreshData(Rent data) {
		String labels = data.getLabel();
		if (!TextUtils.isEmpty(labels)) {
			String[] splitLabels = labels.split(",");
			if (splitLabels.length == 1) {
				mTvLabel1.setVisibility(View.VISIBLE);
				mTvLabel1.setText(splitLabels[0]);
			} else if (splitLabels.length == 2) {
				mTvLabel1.setVisibility(View.VISIBLE);
				mTvLabel2.setVisibility(View.VISIBLE);
				mTvLabel1.setText(splitLabels[0]);
				mTvLabel2.setText(splitLabels[1]);
			} else if (splitLabels.length >= 3) {
				mTvLabel1.setVisibility(View.VISIBLE);
				mTvLabel2.setVisibility(View.VISIBLE);
				mTvLabel3.setVisibility(View.VISIBLE);
				mTvLabel1.setText(splitLabels[0]);
				mTvLabel2.setText(splitLabels[1]);
				mTvLabel3.setText(splitLabels[2]);
			}
		}
		int distance = data.getDistance();
		if (distance < 1000) {
			mTvDistance.setText(distance + "m");
		} else {
			mTvDistance.setText(distance / 1000 + "km");
		}

		mTvTitle.setText(data.getTitle());
		mTvHouseType.setText(data.getHouseType());
		mTvLocation.setText(data.getLocation());
		mTvAmount.setText("￥" + data.getAmount() + "/月");
		Glide.with(UI.getContext()).load(UrlUtils.getImageUrl(data.getTitleImg())).into(mAivHead);

		int status = data.getStatus();
		if (Common.STATUS_1_ON_SHELFING == status) {//有效的条目
			mRlRentStatus.setVisibility(View.GONE);
		} else {
			mRlRentStatus.setVisibility(View.VISIBLE);
			switch (status) {
				case Common.STATUS_0_UNDER_REVIEWING:
					mTvRentStatus.setText("审核中");
					mTvRentStatus.setTextColor(UI.getColor(R.color.colorPrimaryDark));
					mTvRentStatus.setBackground(UI.getDrawable(R.drawable.bg_rotation_text_yellow));
					break;
				case Common.STATUS_2_REVIEW_FAIL:
					mTvRentStatus.setText("审核未通过");
					mTvRentStatus.setTextColor(UI.getColor(R.color.action_red));
					mTvRentStatus.setBackground(UI.getDrawable(R.drawable.bg_rotation_text_red));
					break;
				case Common.STATUS_3_OFF_SHELF_BY_SELF:
					mTvRentStatus.setText("主动下架");
					mTvRentStatus.setTextColor(UI.getColor(R.color.tv_colorPrimaryHint));
					mTvRentStatus.setBackground(UI.getDrawable(R.drawable.bg_rotation_text_gray));
					break;
				case Common.STATUS_4_OFF_SHELF_ILLEGAL:
					mTvRentStatus.setText("违规下架");
					mTvRentStatus.setTextColor(UI.getColor(R.color.action_red));
					mTvRentStatus.setBackground(UI.getDrawable(R.drawable.bg_rotation_text_red));
					break;
				case Common.STATUS_5_OFF_SHELF_COMMON:
					mTvRentStatus.setText("已下架");
					mTvRentStatus.setTextColor(UI.getColor(R.color.tv_colorPrimaryHint));
					mTvRentStatus.setBackground(UI.getDrawable(R.drawable.bg_rotation_text_gray));
					break;
			}
		}
	}

}
