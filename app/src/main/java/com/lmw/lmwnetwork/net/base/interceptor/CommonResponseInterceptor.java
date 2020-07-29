package com.lmw.lmwnetwork.net.base.interceptor;

import android.util.Log;

import com.lmw.lmwnetwork.lib.interceptor.BaseInterceptor;

import java.io.IOException;

import okhttp3.Response;

/**
 * 通用响应拦截器
 */
public class CommonResponseInterceptor extends BaseInterceptor {
    private static final String TAG = "ResponseInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.d(TAG, "requestTime=" + (System.currentTimeMillis() - requestTime));
        return response;
    }
}
