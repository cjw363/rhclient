package com.cjw.rhclient.main.register;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.main.login.LoginActivity;
import com.cjw.rhclient.utils.MatcherUtils;
import com.cjw.rhclient.utils.UI;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterContract.RegisterView {
	@Inject
	RegisterPresenter mRegisterPresenter;

	@BindView(R.id.et_register_name)
	EditText mEtRegisterName;
	@BindView(R.id.et_register_password)
	EditText mEtRegisterPassword;
	@BindView(R.id.et_register_sure_password)
	EditText mEtRegisterSurePassword;
	@BindView(R.id.tv_to_login)
	TextView mTvToLogin;
	@BindView(R.id.bt_register)
	Button mBtRegister;
	@BindView(R.id.auto_tv_school)
	AutoCompleteTextView mAutoTvSchool;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_register;
	}

	@Override
	protected void initView() {
		DaggerRegisterComponent.builder().registerPresenterModule(new RegisterPresenterModule(this, this)).build().inject(this);
	}

	@Override
	public void initData() {
		mRegisterPresenter.getSchoolData();
	}

	@OnClick({R.id.bt_register, R.id.tv_to_login})
	public void OnClick(View v) {
		switch (v.getId()) {
			case R.id.bt_register:
				String name = mEtRegisterName.getText().toString().trim();
				String password = mEtRegisterPassword.getText().toString().trim();
				String surePassword = mEtRegisterSurePassword.getText().toString().trim();
				String school = mAutoTvSchool.getText().toString().trim();
				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(surePassword) || TextUtils.isEmpty(school)) {
					UI.showToast("请完整输入");
				} else if (!MatcherUtils.matcher(name)) {
					UI.showToast("用户名不合法");
				} else if (!MatcherUtils.matcher(password)) {
					UI.showToast("密码不合法");
				} else if (!password.equals(surePassword)) {
					UI.showToast("两次输入密码不一致");
				} else

					mRegisterPresenter.register(name, password, school);
				break;
			case R.id.tv_to_login:
				startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
				finish();
				break;
		}
	}

	@Override
	public void toLogin() {
		Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
		intent.putExtra("register_name", mEtRegisterName.getText().toString().trim());
		startActivity(intent);
		finish();
	}

	@Override
	public void setAutoTvData(List<String> result) {
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result);
		mAutoTvSchool.setAdapter(adapter);
	}

}
