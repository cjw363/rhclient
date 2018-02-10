package com.cjw.rhclient.service;

import com.cjw.rhclient.been.Rent;
import com.cjw.rhclient.http.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RentService {
	@GET("campusList")
	Observable<HttpResult<List<Rent>>> getCampusList(@Query("token") String token, @Query("type") int type);
}
