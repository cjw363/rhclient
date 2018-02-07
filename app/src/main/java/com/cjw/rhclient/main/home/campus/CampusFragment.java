package com.cjw.rhclient.main.home.campus;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.CampusAdapter;
import com.cjw.rhclient.base.BaseFragment;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.main.home.detail.DetailActivity;
import com.cjw.rhclient.utils.UI;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class CampusFragment extends BaseFragment implements CampusContract.CampusView {
	@Inject
	CampusPresenter mCampusPresenter;

	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.swipeRefresh)
	SwipeRefreshLayout mSwipeRefresh;

	@Override
	public int getContentLayoutId() {
		return R.layout.fragment_campus;
	}

	@Override
	protected void initView() {
		DaggerCampusComponent.builder().campusPresenterModule(new CampusPresenterModule(this, getActivity())).build().inject(this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(UI.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
	}

	@Override
	public void initData() {
		mCampusPresenter.getCampusList();
	}

	@Override
	public void showCampusList(List<Rent> result) {
		CampusAdapter campusAdapter = new CampusAdapter(getActivity(), result);
		mRecyclerView.setAdapter(campusAdapter);
		campusAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Rent>() {
			@Override
			public void onItemClick(View view, int position, Rent data) {
				startActivity(new Intent(getActivity(), DetailActivity.class));
			}
		});
	}
}
