package com.cjw.rhclient.main.home.publish;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.cjw.rhclient.R;
import com.cjw.rhclient.base.BaseActivity;
import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.main.home.map.BaiduMapHelper;
import com.cjw.rhclient.main.home.map.MapActivity;
import com.cjw.rhclient.utils.UI;
import com.cjw.rhclient.view.FlowLayout;
import com.cjw.rhclient.view.TypeContentView;
import com.cjw.rhclient.view.dialog.BaseCustomDialog;
import com.cjw.rhclient.view.dialog.BottomDialog;
import com.cjw.rhclient.view.dialog.ContentDialog;
import com.cjw.rhclient.view.wheelview.WheelView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

import static com.cjw.rhclient.R.id.tv_cancel;
import static com.cjw.rhclient.R.id.tv_ok;

@RuntimePermissions
public class PublishActivity extends BaseActivity implements PublishContract.View, RadioGroup.OnCheckedChangeListener {
	private static final int REQUEST_CODE_CHOOSE = 23;
	private static final int MY_PERMISSION_REQUEST_CODE = 6;

	@Inject
	PublishPresenter mPresenter;

	@BindView(R.id.tv_toolbar_title)
	TextView mTvToolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.et_publish_title)
	EditText mEtTitle;
	@BindView(R.id.et_publish_content)
	EditText mEtContent;
	@BindView(R.id.tv_location)
	TextView mTvLocation;
	@BindView(R.id.rg_publish_type)
	RadioGroup mRgType;
	@BindView(R.id.bt_publish)
	Button mBtPublish;
	@BindView(R.id.flowLayout)
	FlowLayout mFlowLayout;
	@BindView(R.id.tcv_amount)
	TypeContentView mTcvAmount;
	@BindView(R.id.tcv_house_type)
	TypeContentView mTcvHouseType;
	@BindView(R.id.tcv_area)
	TypeContentView mTcvArea;
	@BindView(R.id.tcv_bed)
	TypeContentView mTcvBed;
	@BindView(R.id.aiv_pic)
	AppCompatImageView mAivPic;
	@BindView(R.id.ll_pic)
	LinearLayout mLlPic;

	private List<String> beds = new ArrayList<>(Arrays.asList(new String[]{"1个", "2个", "3个", "4个", "5个", "6个", "6个以上"}));
	private List<String> houseType1 = new ArrayList<>(Arrays.asList(new String[]{"1室", "2室", "3室", "4室", "4室以上"}));
	private List<String> houseType2 = new ArrayList<>(Arrays.asList(new String[]{"1厅", "2厅", "2厅以上"}));
	private List<String> houseType3 = new ArrayList<>(Arrays.asList(new String[]{"1卫", "2卫", "2卫以上"}));

	private BottomDialog mBottomDialog;
	private BaseCustomDialog mContentDialog;

	private List<String> checkedLabels = new ArrayList<>();
	private List<Uri> mUris;
	private double mLatitude;
	private double mLongitude;

	@Override
	public int getContentLayoutId() {
		return R.layout.activity_publish;
	}

	@Override
	protected void initView() {
		DaggerPublishComponent.builder().publishPresenterModule(new PublishPresenterModule(this, this)).build().inject(this);
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
		}
		mTvToolbarTitle.setText("发布出租");
		mRgType.setOnCheckedChangeListener(this);
		((RadioButton) mRgType.getChildAt(0)).setChecked(true);//默认选中第一个
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

	private void initFlowLayout() {
		if (mFlowLayout != null) {
			int dp_10 = UI.dip2px(10);
			int dp_5 = UI.dip2px(5);
			mFlowLayout.removeAllViews();
			mFlowLayout.setHorizontalSpacing(dp_10);
			mFlowLayout.setVerticalSpacing(dp_10);
			String[] labelNames = UI.getStringArray(R.array.label_names);
			for (String labelName : labelNames) {
				CheckBox checkBox = new CheckBox(UI.getContext());
				checkBox.setText(labelName);
				checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				checkBox.setTextColor(UI.getColor(R.color.tv_colorPrimaryHint));
				checkBox.setPadding(dp_5, dp_5, dp_5, dp_5);
				checkBox.setGravity(Gravity.CENTER);
				checkBox.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
				checkBox.setBackgroundResource(R.drawable.bg_selector_label);
				checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							checkedLabels.add(buttonView.getText().toString());
						} else {
							checkedLabels.remove(buttonView.getText().toString());
						}
					}
				});
				mFlowLayout.addView(checkBox);
			}
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
		initFlowLayout();
		switch (checkedId) {
			case R.id.rb_type_1:
				mTcvHouseType.setVisibility(View.GONE);
				mTcvArea.setVisibility(View.GONE);
				mTcvBed.setVisibility(View.VISIBLE);
				break;
			case R.id.rb_type_2:
			case R.id.rb_type_3:
			case R.id.rb_type_4:
			case R.id.rb_type_5:
				mTcvHouseType.setVisibility(View.VISIBLE);
				mTcvArea.setVisibility(View.VISIBLE);
				mTcvBed.setVisibility(View.GONE);
				break;
		}
	}

	@OnClick({R.id.tcv_amount, R.id.tcv_house_type, R.id.tcv_area, R.id.tcv_bed, R.id.aiv_pic, R.id.bt_publish})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tcv_amount:
				showAlertDialog("租金", mTcvAmount);
				break;
			case R.id.tcv_house_type:
				showBottomDialog(houseType1, houseType2, houseType3, mTcvHouseType);
				break;
			case R.id.tcv_area:
				showAlertDialog("房屋面积", mTcvArea);
				break;
			case R.id.tcv_bed:
				showBottomDialog(null, beds, null, mTcvBed);
				break;
			case R.id.aiv_pic:
				PublishActivityPermissionsDispatcher.showImageSelectorWithCheck(this);//委托权限调用
				break;
			case R.id.bt_publish:
				showIsPublishDialog();
				break;
			case R.id.rl_location:
				startActivity(new Intent(this, MapActivity.class));
				break;
		}
	}

	@NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
	public void showImageSelector() {
		Matisse.from(this).choose(MimeType.of(MimeType.JPEG, MimeType.PNG)) // 选择 mime 的类型
		  .theme(R.style.Matisse_Dracula) //选择主题 默认是蓝色主题，Matisse_Dracula为黑色主题
		  .countable(false) //是否显示数字
		  .capture(true)  //是否可以拍照
		  .captureStrategy(new CaptureStrategy(true, "com.cjw.rhclient.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
		  .maxSelectable(6) // 图片选择的最多数量
		  //		  .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//图片大小,不设置默认三列
		  //		  .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K)) //添加自定义过滤器
		  .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED).thumbnailScale(0.85f) // 缩略图的比例
		  .imageEngine(new GlideEngine()) // 使用的图片加载引擎
		  .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
	}

	private void showIsPublishDialog() {
		mContentDialog = new ContentDialog.Builder(this).setContent("是否确定发布房屋信息").setOkListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mContentDialog.dismiss();
				Map<String, String> params = getPublishParams();
				if (params != null) {
					if (mUris == null) {
						UI.showToast("未上传图片");
					} else mPresenter.publishRent(mUris, params);
				}
			}
		}).build();
		mContentDialog.showDialog();
	}

	private Map<String, String> getPublishParams() {
		String title = mEtTitle.getText().toString();
		String content = mEtContent.getText().toString();
		String type = (String) mRgType.findViewById(mRgType.getCheckedRadioButtonId()).getTag();
		String location = mTvLocation.getText().toString();
		String amount = mTcvAmount.getContent();
		String houseType = mTcvHouseType.getContent();
		String area = mTcvArea.getContent();
		String bed = mTcvBed.getContent();

		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
			UI.showToast("请完整输入!");
			return null;
		}
		if (TextUtils.isEmpty(location)) {
			UI.showToast("未定位!");
			return null;
		}
		HashMap<String, String> map = new HashMap<>();
		map.put("title", title);
		map.put("content", content);
		map.put("type", type + "");
		map.put("location", location);
		map.put("longitude", mLongitude + "");
		map.put("latitude", mLatitude + "");
		map.put("amount", amount.replace("￥", "").replace("/月", ""));
		map.put("user_id", Session.user.getId() + "");
		if (getResources().getInteger(R.integer.校内出租) == Integer.parseInt(type)) {
			map.put("bed", bed);
			map.put("area", "0");
			map.put("house_type", "");
		} else {
			map.put("bed", "");
			map.put("area", area.replace("平米", ""));
			map.put("house_type", houseType);
		}
		if (checkedLabels.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (String label : checkedLabels) {
				sb.append(label);
				sb.append(",");
			}
			map.put("label", sb.substring(0, sb.length() - 1));
		} else map.put("label", "");
		return map;
	}

	private void showBottomDialog(List<String> left, List<String> center, List<String> right, final TypeContentView tcv) {
		final View view = UI.inflate(R.layout.layout_dialog_bottom);
		//滚轮
		final WheelView wxLeft = (WheelView) view.findViewById(R.id.wheelView_left);
		final WheelView wxCenter = (WheelView) view.findViewById(R.id.wheelView_center);
		final WheelView wxRight = (WheelView) view.findViewById(R.id.wheelView_right);
		TextView tvOK = (TextView) view.findViewById(tv_ok);
		TextView tvCancel = (TextView) view.findViewById(tv_cancel);

		if (left != null) wxLeft.setItems(left, 1);
		if (center != null) wxCenter.setItems(center, 1);
		if (right != null) wxRight.setItems(right, 1);

		//点击确定
		tvOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBottomDialog.dismiss();
				tcv.setContent(wxLeft.getSelectedItem() + wxCenter.getSelectedItem() + wxRight.getSelectedItem());
			}
		});
		//点击取消
		tvCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBottomDialog.dismiss();
			}
		});
		//防止弹出两个窗口
		if (mBottomDialog != null && mBottomDialog.isShowing()) {
			return;
		}

		mBottomDialog = new BottomDialog(this);
		//将布局设置给Dialog
		mBottomDialog.setContentView(view);
		mBottomDialog.show();//显示对话框
	}

	private void showAlertDialog(final String title, final TypeContentView tcv) {
		final EditText editText = new EditText(this);
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		new AlertDialog.Builder(this).setTitle(title).setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String input = editText.getText().toString();
				int num = 0;
				if (TextUtils.isEmpty(input)) {
					UI.showToast("请输入有效数字");
					return;
				} else {
					num = Integer.parseInt(input);
					if (num <= 0) {
						UI.showToast("请输入有效数字");
						return;
					}
				}
				if (title.equals("租金")) {
					tcv.setContent("￥" + num + "/月");
				} else {
					tcv.setContent(num + "平米");
				}
			}
		}).show();
	}

	@Override
	public void initData() {
		PublishActivityPermissionsDispatcher.startLocationWithCheck(this);//委托权限调用
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PublishActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
			mUris = Matisse.obtainResult(data);

			mLlPic.removeViews(0, mLlPic.getChildCount() - 1);//移除之前的图片
			for (int i = 0; i < mUris.size(); i++) {
				AppCompatImageView image = new AppCompatImageView(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UI.dip2px(65), UI.dip2px(65));
				params.rightMargin = UI.dip2px(5);
				image.setLayoutParams(params);
				image.setScaleType(AppCompatImageView.ScaleType.CENTER_CROP);

				Glide.with(UI.getContext()).load(mUris.get(i)).into(image);
				mLlPic.addView(image, i);
			}
		}
	}

	@NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
	public void startLocation() {
		BaiduMapHelper.startLocation(getApplicationContext(), new BDAbstractLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				//获取经度信息
				mLongitude = location.getLongitude();
				//获取纬度信息
				mLatitude = location.getLatitude();
				String address = location.getAddrStr().replace("中国", "");    //获取详细地址信息
				mTvLocation.setText(address);
			}
		});
	}

	@OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
	public void onPermissionDenied() {
		UI.showToast("部分功能需要必要权限!");
	}
}
