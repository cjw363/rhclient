package com.cjw.rhclient.main.home.detail;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity implements DetailContract.DetailView{
	@Inject
	DetailPresenter mDetailPresenter;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_detail;
	}

	@Override
	protected void initView() {
		DaggerDetailComponent.builder().detailPresenterModule(new DetailPresenterModule(this,this)).build().inject(this);
	}
}
