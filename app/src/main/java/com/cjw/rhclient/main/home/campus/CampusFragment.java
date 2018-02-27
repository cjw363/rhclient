package com.cjw.rhclient.main.home.campus;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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
import com.cjw.rhclient.view.dialog.ContentDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class CampusFragment extends BaseFragment implements CampusContract.CampusView , SwipeRefreshLayout.OnRefreshListener{
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
		DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
		itemDecoration.setDrawable(UI.getDrawable(R.drawable.item_divider));
		mRecyclerView.addItemDecoration(itemDecoration);
		mSwipeRefresh.setOnRefreshListener(this);
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
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				intent.putExtra("data", data);
				startActivity(intent);
			}
		});
	}

	@Override
	public void showNoData() {
		new ContentDialog.Builder(getActivity()).setSingleButton().setContent("暂无数据").build().showDialog();
	}

	@Override
	public void onRefresh() {
		mCampusPresenter.getCampusList();
		mSwipeRefresh.setRefreshing(false);
	}
}
