package com.cjw.rhclient.main.home.detail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.BBsAdapter;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.been.BBs;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.main.home.map.MapActivity;
import com.cjw.rhclient.utils.DateUtil;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.utils.UrlUtils;
import com.cjw.rhclient.view.ScrollRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailContract.DetailView {
	@Inject
	DetailPresenter mDetailPresenter;

	@BindView(R.id.tv_toolbar_title)
	TextView mTvToolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.aiv_head)
	AppCompatImageView mAivHead;
	@BindView(R.id.tv_name)
	TextView mTvName;
	@BindView(R.id.tv_amount)
	TextView mTvAmount;
	@BindView(R.id.tv_content)
	TextView mTvContent;
	@BindView(R.id.cb_favorite)
	CheckBox mCbFavorite;
	@BindView(R.id.bt_bbs)
	Button mBtBbs;
	@BindView(R.id.et_input)
	EditText mEtInput;
	@BindView(R.id.ll_pic)
	LinearLayout mLlPic;
	@BindView(R.id.tv_location)
	TextView mTvLocation;
	@BindView(R.id.rcv_bbs)
	ScrollRecyclerView mRcvBBs;
	@BindView(R.id.tv_type)
	TextView mTvType;
	@BindView(R.id.tv_house_type)
	TextView mTvHouseType;
	@BindView(R.id.tv_amount2)
	TextView mTvAmount2;
	@BindView(R.id.tv_label)
	TextView mTvLabel;
	@BindView(R.id.tv_area)
	TextView mTvArea;
	@BindView(R.id.tv_time)
	TextView mTvTime;
	private Rent mData;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_detail;
	}

	@Override
	protected void initView() {
		DaggerDetailComponent.builder().detailPresenterModule(new DetailPresenterModule(this, this)).build().inject(this);
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}

		LinearLayoutManager layoutManager = new LinearLayoutManager(UI.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRcvBBs.setLayoutManager(layoutManager);
		mTvToolbarTitle.setText("房屋详情");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return true;
	}

	@Override
	public void initData() {
		mData = (Rent) getIntent().getSerializableExtra("data");
		if (mData != null) {
			mLlPic.removeAllViews();
			mTvName.setText(mData.getTitle());
			mTvContent.setText(mData.getContent());
			mTvAmount.setText("￥" + mData.getAmount() + "/月");
			mTvLocation.setText(mData.getLocation());

			String type = "";
			switch (mData.getType()) {
				case 1:
					type = "整租";
					break;
				case 2:
					type = "单间";
					break;
				case 3:
					type = "日租";
					break;
				case 4:
					type = "办公";
					break;
				case 5:
					type = "校内出租";
					break;
				default:
					type = "其他";
					break;
			}

			mTvType.setText("类型：" + type);
			mTvHouseType.setText("户型：" + mData.getHouseType());
			mTvAmount2.setText("租金：" + mData.getAmount() + "/月");
			mTvLabel.setText("标签：" + mData.getLabel());
			mTvArea.setText("面积：" + mData.getArea() + "平米");
			mTvTime.setText("时间：" + DateUtil.stampToDate(mData.getTime()));

			String titleImg = mData.getTitleImg();
			if (!TextUtils.isEmpty(titleImg)) setPic(titleImg);

			String otherImg = mData.getOtherImg();
			if (!TextUtils.isEmpty(otherImg)) {
				String[] split = otherImg.split(",");
				for (String url : split) {
					setPic(url);
				}
			}
			mDetailPresenter.getBBsList(mData.getId());
		}
	}

	private void setPic(String url) {
		AppCompatImageView image = new AppCompatImageView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UI.dip2px(180));
		params.bottomMargin = UI.dip2px(5);
		image.setLayoutParams(params);
		image.setScaleType(AppCompatImageView.ScaleType.CENTER_CROP);

		Glide.with(UI.getContext()).load(UrlUtils.getImageUrl(url)).into(image);
		mLlPic.addView(image);
	}

	@OnClick({R.id.rl_location, R.id.bt_bbs})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_location:
				Intent intent = new Intent(this, MapActivity.class);
				intent.setAction(MapActivity.ACTION_MAP_NAVIGATION);
				intent.putExtra("target_longitude", mData.getLongitude());
				intent.putExtra("target_latitude", mData.getLatitude());
				intent.putExtra("mine_longitude", Session.location.getLongitude());
				intent.putExtra("mine_latitude", Session.location.getLatitude());
				startActivity(intent);
				break;
			case R.id.bt_bbs:
				String input = mEtInput.getText().toString().trim();
				if (TextUtils.isEmpty(input)) {
					UI.showToast("输入不能为空");
				} else {
					mDetailPresenter.bbs(input, mData.getId());
				}
				break;
		}
	}

	@Override
	public void showBBsList(List<BBs> result) {
		mEtInput.setText("");
		mRcvBBs.setAdapter(new BBsAdapter(this, result));
	}
}
