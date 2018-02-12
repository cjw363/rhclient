package com.cjw.rhclient.main.home.publish;

import android.net.Uri;

import java.util.List;

public interface PublishContract {
	interface View{}
	interface Presenter{
		void showImageSelector();

		void publishRent(List<Uri> uris);
	}
}
