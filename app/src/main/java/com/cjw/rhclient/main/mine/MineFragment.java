package com.cjw.rhclient.main.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseFragment;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.main.login.LoginActivity;
import com.cjw.rhclient.view.TypeContentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements MineContract.MineView {

	@Inject
	MinePresenter mMinePresenter;

	@BindView(R.id.tv_name)
	TextView mTvName;
	@BindView(R.id.tv_school)
	TextView mTvSchool;
	@BindView(R.id.tcv_mine_favorite)
	TypeContentView mTcvMineFavorite;
	@BindView(R.id.tcv_mine_publish)
	TypeContentView mTcvMinePublish;
	@BindView(R.id.bt_login_out)
	Button mBtLoginOut;

	@Override
	public int getContentLayoutId() {
		return R.layout.fragment_mine;
	}

	@Override
	protected void initView() {
		DaggerMineComponent.builder().minePresenterModule(new MinePresenterModule(this, getActivity())).build().inject(this);
		mTvName.setText(Session.user.getName());
		mTvSchool.setText(Session.user.getSchoolName());
	}

	@OnClick({R.id.tcv_mine_favorite, R.id.tcv_mine_publish, R.id.bt_login_out})
	public void onClickView(View v) {
		switch (v.getId()) {
			case R.id.tcv_mine_favorite:
				break;
			case R.id.tcv_mine_publish:
				break;
			case R.id.bt_login_out:
				mMinePresenter.outLogin();
				break;
		}
	}

	@Override
	public void toLogin() {
		startActivity(new Intent(getActivity(), LoginActivity.class));
		getActivity().finish();
	}
}
