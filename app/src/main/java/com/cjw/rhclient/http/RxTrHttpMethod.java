package com.cjw.rhclient.http;

import com.cjw.rhclient.base.BaseApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 17-2-23.
 */
public class RxTrHttpMethod {

    private final static long DEFAULT_TIMEOUT = 5;

    private static class SingletonHolder {
        private static final RxTrHttpMethod INSTANCE = new RxTrHttpMethod();
    }

    public static RxTrHttpMethod getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * create a service
     *
     * @GET("data/all/20/{page}")
     * Observable<GankResultBean> getAndroidData(@Path("page") int page);
     * }
     */
    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = BaseApplication.getBaseUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
//        File httpCacheDirectory = new File(FileUtils.getCacheDir(mContext), "OkHttpCache");
//        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        return httpClientBuilder.build();
    }
}
