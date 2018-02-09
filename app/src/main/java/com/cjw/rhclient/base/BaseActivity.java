package com.cjw.rhclient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.view.dialog.LoadingDialog;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
	public static boolean hasWindowFocus;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(getContentLayoutId());
		ButterKnife.bind(this);
		initView();

		UI.putActivity(this);//往栈添加当前activity
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	public abstract int getContentLayoutId();

	protected abstract void initView();

	public void initData() {}

	@Override
	protected void onStop() {
		super.onStop();
		LoadingDialog.close();
		hasWindowFocus = false;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		hasWindowFocus = hasFocus;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		UI.removeActivity(this);
	}
}
