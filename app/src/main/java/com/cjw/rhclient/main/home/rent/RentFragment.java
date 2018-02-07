package com.cjw.rhclient.main.home.rent;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cjw.rhclient.R;
import com.cjw.rhclient.adapter.RentAdapter;
import com.cjw.rhclient.base.BaseFragment;
import com.cjw.rhclient.base.BaseRecyclerViewAdapter;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.main.home.detail.DetailActivity;
import com.cjw.rhclient.utils.UI;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.cjw.rhclient.R.id.rb_sort_1;


public class RentFragment extends BaseFragment implements RentContract.RentView, RadioGroup.OnCheckedChangeListener {
	@Inject
	RentPresenter mRentPresenter;

	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.swipeRefresh)
	SwipeRefreshLayout mSwipeRefresh;
	@BindView(rb_sort_1)
	RadioButton mRbSort1;
	@BindView(R.id.rb_sort_2)
	RadioButton mRbSort2;
	@BindView(R.id.rb_sort_3)
	RadioButton mRbSort3;
	@BindView(R.id.rb_sort_4)
	RadioButton mRbSort4;
	@BindView(R.id.rg_sort)
	RadioGroup mRgSort;

	private static final int ARROW_UP = 1;
	private static final int ARROW_DOWN = 2;

	@Override
	public int getContentLayoutId() {
		return R.layout.fragment_rent;
	}

	@Override
	protected void initView() {
		DaggerRentComponent.builder()
		  .rentPresenterModule(new RentPresenterModule(this, getActivity()))
		  .build()
		  .inject(this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(UI.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);

		Drawable drawable = UI.getDrawable(R.drawable.arrow_rank_down);
		drawable.setBounds(0, 0, UI.dip2px(20), UI.dip2px(20));
		mRbSort4.setCompoundDrawables(null, null, drawable, null);
		mRbSort4.setTag(ARROW_DOWN);
		mRgSort.setOnCheckedChangeListener(this);
	}

	@Override
	public void initData() {
		Bundle bundle = getArguments();
		if (bundle != null) bundle.getInt("type");
		mRentPresenter.getRentList();
	}

	@Override
	public void showRentList(List<Rent> result) {
		RentAdapter rentAdapter = new RentAdapter(getActivity(), result);
		mRecyclerView.setAdapter(rentAdapter);
		rentAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Rent>() {
			@Override
			public void onItemClick(View view, int position, Rent data) {
				startActivity(new Intent(getActivity(), DetailActivity.class));
			}
		});
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
		switch (id) {
			case R.id.rb_sort_1:
				break;
			case R.id.rb_sort_2:
				break;
			case R.id.rb_sort_3:
				break;
			case R.id.rb_sort_4:
				break;
		}
	}

	@OnClick(R.id.rb_sort_4)
	public void onClick(View v) {
		int state = (Integer) v.getTag();
		Drawable drawable;
		if (ARROW_DOWN == state) {
			v.setTag(ARROW_UP);
			drawable = UI.getDrawable(R.drawable.arrow_rank_up);
		} else {
			v.setTag(ARROW_DOWN);
			drawable = UI.getDrawable(R.drawable.arrow_rank_down);
		}
		drawable.setBounds(0, 0, UI.dip2px(20), UI.dip2px(20));
		mRbSort4.setCompoundDrawables(null, null, drawable, null);
	}
}
