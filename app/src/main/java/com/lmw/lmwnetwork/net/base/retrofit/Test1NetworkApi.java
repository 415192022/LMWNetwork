package com.lmw.lmwnetwork.net.base.retrofit;

import android.widget.Toast;

import com.lmw.lmwnetwork.lib.core.BaseNetworkApi;
import com.lmw.lmwnetwork.lib.exception.ServerException;
import com.lmw.lmwnetwork.net.pojo.no.Test1BaseResponseNo;
import com.lmw.lmwnetwork.net.utils.TecentUtil;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Test1NetworkApi extends BaseNetworkApi {
    private static volatile Test1NetworkApi sInstance;

    public static Test1NetworkApi getInstance() {
        if (sInstance == null) {
            synchronized (Test1NetworkApi.class) {
                if (sInstance == null) {
                    sInstance = new Test1NetworkApi();
                }
            }
        }
        return sInstance;
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String timeStr = TecentUtil.getTimeStr();
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Source", "source");
                builder.addHeader("Authorization", TecentUtil.getAuthorization(timeStr));
                builder.addHeader("Date", timeStr);
                return chain.proceed(builder.build());
            }
        };
    }

    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                //response中code码不会0 出现错误
                if (response instanceof Test1BaseResponseNo && ((Test1BaseResponseNo) response).showapiResCode != 0) {
                    ServerException exception = new ServerException();
                    exception.code = ((Test1BaseResponseNo) response).showapiResCode;
                    exception.message = ((Test1BaseResponseNo) response).showapiResError != null ? ((Test1BaseResponseNo) response).showapiResError : "";
                    throw exception;
                }
                return response;
            }
        };
    }

    @Override
    public String getFormal() {
        Toast.makeText(BaseNetworkApi.getApplication(), "正式环境", Toast.LENGTH_SHORT).show();
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }

    @Override
    public String getTest() {
        Toast.makeText(BaseNetworkApi.getApplication(), "测试环境", Toast.LENGTH_SHORT).show();
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }
}
