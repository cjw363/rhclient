package com.cjw.rhclient.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjw.rhclient.utils.LogUtils;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(getContentLayoutId(), null);
		ButterKnife.bind(this, mView);
		initView();
		return mView;
	}

	public abstract int getContentLayoutId();

	protected abstract void initView();

	protected void initData() {}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {// 当fragment处于可见状态
			if (mView != null) {
				initData();
				LogUtils.d(getClass().getName() + "++可视化++>" + "initData");
			}
		}
	}

	private boolean isFirstCreate = false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getUserVisibleHint() && !isFirstCreate) {
			initData();
			isFirstCreate = true;
			LogUtils.d(getClass().getName() + "++ActivityCreate++>" + "initData");
		}
	}
}
