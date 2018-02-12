package com.cjw.rhclient.main.home.publish;

import android.net.Uri;

import java.util.List;
import java.util.Map;

public interface PublishContract {
	interface View{}
	interface Presenter{
		void showImageSelector();

		void publishRent(List<Uri> uris, Map<String,String> params);
	}
}
