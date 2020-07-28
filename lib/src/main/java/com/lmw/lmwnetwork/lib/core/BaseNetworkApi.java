package com.lmw.lmwnetwork.lib.core;


import android.content.Context;

import com.lmw.lmwnetwork.lib.environment.IEnvironment;
import com.lmw.lmwnetwork.lib.exception.BaseExceptionHandle;
import com.lmw.lmwnetwork.lib.interceptor.BaseInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseNetworkApi implements IEnvironment {

    private static INetworkRequiredInfo iNetworkRequiredInfo;

    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

    private String mBaseUrl;

    private OkHttpClient mOkHttpClient;

    private static boolean mIsFormal = true;

    private static List<Interceptor> mInterceptorList = new ArrayList<>();

    private static BaseExceptionHandle mIExceptionHandle;

    public static Context getApplication() {
        return mApplication;
    }

    public static void setApplication(Context mApplication) {
        BaseNetworkApi.mApplication = mApplication;
    }

    private static Context mApplication;

    public BaseNetworkApi() {
        if (!mIsFormal) {
            mBaseUrl = getTest();
        }
        mBaseUrl = getFormal();
    }


    public static void init(List<Interceptor> interceptorList, INetworkRequiredInfo requestInfo, BaseExceptionHandle iExceptionHandle) {
        iNetworkRequiredInfo = requestInfo;
        mInterceptorList = interceptorList;
        mIExceptionHandle = iExceptionHandle;
        if (iNetworkRequiredInfo == null) return;
        mApplication = requestInfo.getApplicationContext();
//        mIsFormal = EnvironmentActivity.isOfficialEnvironment(networkRequiredInfo.getApplicationContext());
    }

    protected Retrofit getRetrofit(Class service) {
        if (retrofitHashMap.get(mBaseUrl + service.getName()) != null) {
            return retrofitHashMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(mBaseUrl);
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        retrofitHashMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }
            int cacheSize = 100 * 1024 * 1024; // 10MB
            okHttpClientBuilder.cache(new Cache(iNetworkRequiredInfo.getApplicationContext().getCacheDir(), cacheSize));
            for (Interceptor interceptor : mInterceptorList) {
                okHttpClientBuilder.addInterceptor(interceptor);
            }
            if (iNetworkRequiredInfo != null && (iNetworkRequiredInfo.isDebug())) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okHttpClientBuilder.build();
        }
        return mOkHttpClient;
    }


    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = (Observable<T>) upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(getAppErrorHandler())
                        .onErrorResumeNext(new BaseInterceptor.HttpErrorHandler<T>(mIExceptionHandle));
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();
}