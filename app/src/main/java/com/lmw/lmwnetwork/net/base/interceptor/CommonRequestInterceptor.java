package com.lmw.lmwnetwork.net.base.interceptor;


import com.blankj.utilcode.util.AppUtils;
import com.lmw.lmwnetwork.lib.interceptor.BaseInterceptor;
import com.lmw.lmwnetwork.lib.core.INetworkRequiredInfo;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 通用请求拦截器
 */
public class CommonRequestInterceptor extends BaseInterceptor {
    private INetworkRequiredInfo requiredInfo;
    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo){
        this.requiredInfo = requiredInfo;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", AppUtils.getAppVersionCode() + "");
        //builder.cacheControl(CacheControl.FORCE_CACHE);
        return chain.proceed(builder.build());
    }
}
