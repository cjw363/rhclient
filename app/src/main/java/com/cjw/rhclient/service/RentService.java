package com.cjw.rhclient.service;

import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.http.HttpResult;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface RentService {
	@GET("campusList")
	Observable<HttpResult<List<Rent>>> getCampusList(@Query("token") String token, @Query("type") int type);

	@Multipart
	@POST("publishRent")
	Observable<HttpResult<String>> publish(@Query("token") String token, @PartMap Map<String, RequestBody> files, @PartMap Map<String, String> map);
}
