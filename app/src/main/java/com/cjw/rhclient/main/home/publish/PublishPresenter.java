package com.cjw.rhclient.main.home.publish;

import android.content.Context;
import android.net.Uri;

import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.http.HttpResult;
import com.cjw.rhclient.http.HttpResultSubscriber;
import com.cjw.rhclient.http.RxDoOnSubscribe;
import com.cjw.rhclient.http.RxSchedulers;
import com.cjw.rhclient.http.RxTrHttpMethod;
import com.cjw.rhclient.service.RentService;
import com.cjw.rhclient.utils.FileUtil;
import com.cjw.rhclient.view.dialog.ContentDialog;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;

class PublishPresenter implements PublishContract.Presenter {
	private final PublishContract.View mView;
	private final Context mContext;

	@Inject
	PublishPresenter(Context context, PublishContract.View mHomeView) {
		this.mContext = context;
		this.mView = mHomeView;
	}

	@Override
	public void publishRent(List<Uri> uris, Map<String, String> params) {
		Map<String, RequestBody> partMap = new HashMap<>();
		for (Uri uri : uris) {
			String path = FileUtil.getPath(mContext, uri);
			if (path != null) {
				File file = new File(path);
				RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
				partMap.put("files\"; filename=\"" + file.getName(), fileBody);
			}
		}
		RxTrHttpMethod.getInstance().createService(RentService.class).publish(Session.user.getToken(), partMap, params).compose(RxSchedulers.<HttpResult<String>>defaultSchedulers()).doOnSubscribe(new RxDoOnSubscribe(mContext)).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new HttpResultSubscriber<String>(mContext) {
			@Override
			public void _onSuccess(String result) {
				new ContentDialog.Builder(mContext).setContent("发布成功").setSingleButton().build().showDialog();
			}
		});
	}
}
