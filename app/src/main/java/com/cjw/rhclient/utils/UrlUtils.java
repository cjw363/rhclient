package com.cjw.rhclient.utils;

import com.cjw.rhclient.been.Session;
import com.cjw.rhclient.been.common.Common;

public class UrlUtils {
	public static String getImageUrl(String imgUrl) {
		return Common.baseUrl + "loadFile?filePath=" + imgUrl + "&token=" + Session.user.getToken();
	}
}
