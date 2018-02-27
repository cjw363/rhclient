package com.cjw.rhclient.main.mine.publish;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.RentAdapter;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.main.home.detail.DetailActivity;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.utils.viewutils.OnRecyclerItemClickListener;
import com.cjw.rhclient.view.PopupList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MyPublishActivity extends BaseActivity implements MyPublishContract.MyPublishView, SwipeRefreshLayout.OnRefreshListener {
	@Inject
	MyPublishPresenter mPresenter;

	@BindView(R.id.tv_toolbar_title)
	TextView mTvToolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.swipeRefresh)
	SwipeRefreshLayout mSwipeRefresh;

	@Override
	public int getContentLayoutId() {
		return R.layout.layout_my_publish;
	}

	@Override
	protected void initView() {
		DaggerMyPublishComponent.builder().myPublishPresenterModule(new MyPublishPresenterModule(this, this)).build().inject(this);
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}
		mTvToolbarTitle.setText("发布出租");

		LinearLayoutManager layoutManager = new LinearLayoutManager(UI.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		mSwipeRefresh.setOnRefreshListener(this);
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
		mPresenter.getMyPublish();
	}

	@Override
	public void onRefresh() {
		mPresenter.getMyPublish();
		mSwipeRefresh.setRefreshing(false);
	}

	@Override
	public void showMyPublishList(List<Rent> result) {
		RentAdapter rentAdapter = new RentAdapter(this, result);
		mRecyclerView.setAdapter(rentAdapter);
		rentAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Rent>() {
			@Override
			public void onItemClick(View view, int position, Rent data) {
				Intent intent = new Intent(MyPublishActivity.this, DetailActivity.class);
				intent.putExtra("data", data);
				startActivity(intent);
			}
		});
		mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
			@Override
			public void onItemLOngClick(View view, int position, MotionEvent event) {
				List<String> popupMenuItemList = new ArrayList<>();
				popupMenuItemList.add("下架");
				popupMenuItemList.add("删除");
				PopupList popupList = new PopupList(MyPublishActivity.this);
				popupList.showPopupListWindow(view, position, event.getRawX(), event.getRawY(), popupMenuItemList, new PopupList.PopupListListener() {
					@Override
					public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
						return true;
					}

					@Override
					public void onPopupListClick(View contextView, int contextPosition, int position) {
						UI.showToast(position + "");
					}
				});
			}
		});
	}
}
