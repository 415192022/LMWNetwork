package com.lmw.lmwnetwork.net;


import com.lmw.lmwnetwork.lib.interceptor.BaseInterceptor;
import com.lmw.lmwnetwork.lib.core.INetworkRequiredInfo;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor extends BaseInterceptor {
    private INetworkRequiredInfo requiredInfo;
    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo){
        this.requiredInfo = requiredInfo;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        //builder.cacheControl(CacheControl.FORCE_CACHE);
        builder.addHeader("os", "android");
        builder.addHeader("appVersion",this.requiredInfo.getAppVersionCode());
        return chain.proceed(builder.build());
    }
}
