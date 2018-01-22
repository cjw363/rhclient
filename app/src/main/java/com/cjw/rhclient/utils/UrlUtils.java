package com.cjw.rhclient.utils;

import com.cjw.rhclient.base.BaseApplication;

public class UrlUtils {
	public static String getImageUrl(String imgUrl) {
		return BaseApplication.getBaseUrl() + "loadFile?filePath=" + imgUrl + "&token=" + UI.getUser().getToken();
	}
}
