package com.cjw.rhclient.utils;

import com.cjw.rhclient.base.BaseApplication;
import com.cjw.rhclient.been.Session;

public class UrlUtils {
	public static String getImageUrl(String imgUrl) {
		return BaseApplication.getBaseUrl() + "loadFile?filePath=" + imgUrl + "&token=" + Session.user.getToken();
	}
}
