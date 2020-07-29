package com.lmw.lmwnetwork;

import android.app.Application;

import com.lmw.lmwnetwork.lib.core.BaseNetworkApi;
import com.lmw.lmwnetwork.net.base.interceptor.CommonRequestInterceptor;
import com.lmw.lmwnetwork.net.base.interceptor.CommonResponseInterceptor;
import com.lmw.lmwnetwork.net.base.exception.ExceptionHandle;
import com.lmw.lmwnetwork.net.NetworkRequestInfo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNetwork();
    }

    private void initNetwork() {
        List<Interceptor> interceptorList = new ArrayList<>();
        NetworkRequestInfo requestInfo = new NetworkRequestInfo(this);
        CommonRequestInterceptor commonRequestInterceptor = new CommonRequestInterceptor(requestInfo);
        CommonResponseInterceptor commonResponseInterceptor = new CommonResponseInterceptor();

        interceptorList.add(commonRequestInterceptor);
        interceptorList.add(commonResponseInterceptor);

        ExceptionHandle exceptionHandle = new ExceptionHandle();
        BaseNetworkApi.init(interceptorList, requestInfo, exceptionHandle);
    }

}
