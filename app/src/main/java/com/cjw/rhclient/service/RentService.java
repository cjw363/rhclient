package com.cjw.rhclient.service;

import com.cjw.rhclient.been.BBs;
import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.http.HttpResult;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface RentService {
	@FormUrlEncoded
	@POST("campusList")
	Observable<HttpResult<List<Rent>>> getCampusList(@FieldMap Map<String, String> map);

	@Multipart
	@POST("publishRent")
	Observable<HttpResult<String>> publish(@Query("token") String token, @PartMap Map<String, RequestBody> files, @PartMap Map<String, String> map);

	@FormUrlEncoded
	@POST("bbs")
	Observable<HttpResult<List<BBs>>> bbs(@FieldMap Map<String, String> map);

	@FormUrlEncoded
	@POST("bbsList")
	Observable<HttpResult<List<BBs>>> getBBsList(@FieldMap Map<String, String> map);

	@FormUrlEncoded
	@POST("myPublishList")
	Observable<HttpResult<List<Rent>>> getMyPublishList(@FieldMap Map<String, String> map);
}
