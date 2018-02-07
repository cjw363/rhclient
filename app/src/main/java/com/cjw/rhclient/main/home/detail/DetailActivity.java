package com.cjw.rhclient.main.home.detail;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailActivity extends BaseActivity implements DetailContract.DetailView{
	@Inject
	DetailPresenter mDetailPresenter;

	@BindView(R.id.tv_toolbar_title)
	TextView mTvToolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar mToolbar;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_detail;
	}

	@Override
	protected void initView() {
		DaggerDetailComponent.builder().detailPresenterModule(new DetailPresenterModule(this,this)).build().inject(this);
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}
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
}
