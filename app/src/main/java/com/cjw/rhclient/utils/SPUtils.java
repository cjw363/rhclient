package com.cjw.rhclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

	public static void putString(String key, String str) {
		SharedPreferences.Editor sp = UI.getContext().getSharedPreferences("sp_rent_house", Context.MODE_PRIVATE).edit();
		sp.putString(key, str);
		sp.apply();
	}

	public static String getString(String key) {
		SharedPreferences sp = UI.getContext().getSharedPreferences("sp_rent_house", Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
}
