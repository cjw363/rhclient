package com.cjw.rhclient.main.home.publish;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;

import com.cjw.rhclient.R;
import com.cjw.rhclient.http.HttpResult;
import com.cjw.rhclient.http.HttpResultSubscriber;
import com.cjw.rhclient.http.RxDoOnSubscribe;
import com.cjw.rhclient.http.RxSchedulers;
import com.cjw.rhclient.http.RxTrHttpMethod;
import com.cjw.rhclient.service.RentService;
import com.cjw.rhclient.utils.FileUtil;
import com.cjw.rhclient.utils.UI;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;

class PublishPresenter implements PublishContract.Presenter {
	private static final int REQUEST_CODE_CHOOSE = 23;

	private final PublishContract.View mView;
	private final Context mContext;

	@Inject
	PublishPresenter(Context context, PublishContract.View mHomeView) {
		this.mContext = context;
		this.mView = mHomeView;
	}

	@Override
	public void showImageSelector() {
		Matisse.from((PublishActivity) mContext).choose(MimeType.of(MimeType.JPEG, MimeType.PNG)) // 选择 mime 的类型
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

	@Override
	public void publishRent(List<Uri> uris, Map<String,String> params) {
		Map<String, RequestBody> partMap = new HashMap<>();
		for (Uri uri : uris) {
			String path = FileUtil.getPath(mContext, uri);
			if (path != null) {
				File file = new File(path);
				RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
				partMap.put("files\"; filename=\"" + file.getName(), fileBody);
			}
		}
		RxTrHttpMethod.getInstance().createService(RentService.class).publish(UI.getUser().getToken(), partMap, params).compose(RxSchedulers.<HttpResult<String>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<String>(mContext) {
			@Override
			public void _onSuccess(String result) {
			}
		});
	}
}
