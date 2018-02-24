package com.cjw.rhclient.main.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.been.Location;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.been.User;
import com.cjw.rhclient.main.MainActivity;
import com.cjw.rhclient.main.register.RegisterActivity;
import com.cjw.rhclient.utils.MatcherUtils;
import com.cjw.rhclient.utils.SPUtils;
import com.cjw.rhclient.utils.UI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

	@Inject
	LoginPresenter mLoginPresenter;
	@BindView(R.id.et_login_name)
	EditText mEtLoginName;
	@BindView(R.id.et_login_password)
	EditText mEtLoginPassword;
	@BindView(R.id.tv_to_register)
	TextView mTvToRegister;
	@BindView(R.id.bt_login)
	Button mBtLogin;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_login;
	}

	@Override
	protected void initView() {
		DaggerLoginComponent.builder().loginPresenterModule(new LoginPresenterModule(this, this)).build().inject(this);
	}

	@Override
	public void initData() {
		mEtLoginName.setText(SPUtils.getString("name"));//读取数据库
		mEtLoginPassword.setText(SPUtils.getString("password"));

		Intent intent = getIntent();
		String registerName = intent.getStringExtra("register_name");
		if (!TextUtils.isEmpty(registerName)) mEtLoginName.setText(registerName);
	}

	@OnClick({R.id.bt_login, R.id.tv_to_register})
	public void OnClick(View v) {
		switch (v.getId()) {
			case R.id.bt_login:
				String name = mEtLoginName.getText().toString().trim();
				String password = mEtLoginPassword.getText().toString().trim();
				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
					UI.showToast("请完整输入");
				} else if (!MatcherUtils.matcher(name)) {
					UI.showToast("用户名不合法");
				} else if (!MatcherUtils.matcher(password)) {
					UI.showToast("密码不合法");
				} else

					mLoginPresenter.login(name, password);
				break;
			case R.id.tv_to_register:
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
				finish();
				break;
		}
	}

	@Override
	public void toHome(User result) {
		//登录成功，保存登录用户
		SPUtils.putString("name", result.getName());
		SPUtils.putString("password", result.getPassword());
		Session.user = result;
		Session.location = new Location();

		startActivity(new Intent(LoginActivity.this, MainActivity.class));
		finish();
	}
}
