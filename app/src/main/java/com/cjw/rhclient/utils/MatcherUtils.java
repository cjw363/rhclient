package com.cjw.rhclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtils {
	public static boolean matcher(String input) {
		// 编译正则表达式
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z]{6,12}$");
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.find();
	}
}
