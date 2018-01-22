package com.cjw.rhclient.service;

import com.cjw.rhclient.http.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface SchoolService {
	@GET("login/schoolName")
	Observable<HttpResult<List<String>>> getSchoolData();
}
