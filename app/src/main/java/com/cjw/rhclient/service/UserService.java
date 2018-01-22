package com.cjw.rhclient.service;

import com.cjw.rhclient.been.User;
import com.cjw.rhclient.http.HttpResult;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface UserService {
	@FormUrlEncoded
	@POST("login")
	Observable<HttpResult<User>> login(@FieldMap Map<String, String> map);

	@FormUrlEncoded
	@POST("login/register")
	Observable<HttpResult<String>> register(@FieldMap Map<String, String> map);
}
