package com.cjw.rhclient.main.home.detail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.cjw.rhclient.main.home.map.MapActivity;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.utils.UrlUtils;

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
	RecyclerView mRcvBBs;
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
			mTvName.setText(mData.getTitle());
			mTvContent.setText(mData.getContent());
			mTvAmount.setText("￥" + mData.getAmount() + "/月");

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
				startActivity(new Intent(this, MapActivity.class));
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
