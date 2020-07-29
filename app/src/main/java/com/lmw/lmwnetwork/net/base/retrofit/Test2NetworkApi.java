package com.lmw.lmwnetwork.net.base.retrofit;

import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.lmw.lmwnetwork.lib.core.BaseNetworkApi;
import com.lmw.lmwnetwork.lib.exception.ServerException;
import com.lmw.lmwnetwork.net.pojo.no.Test1BaseResponseNo;
import com.lmw.lmwnetwork.net.authorization.PersistentAuthor;
import com.lmw.lmwnetwork.net.authorization.cache.Author;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Function;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class Test2NetworkApi extends BaseNetworkApi {

    private static volatile Test2NetworkApi sInstance;

    //POST/GET的参数
    private Map<String, String> paramsMap = new HashMap<>();

    //请求头参数
    private Map<String, String> headerParamsMap = new HashMap<>();

    public static Test2NetworkApi getInstance() {
        if (sInstance == null) {
            synchronized (Test2NetworkApi.class) {
                if (sInstance == null) {
                    sInstance = new Test2NetworkApi();
                }
            }
        }
        return sInstance;
    }

    private Test2NetworkApi() {
        paramsMap.putAll(generateCommonParams());
    }

    /**
     * 生成公共参数
     *
     * @return
     */
    public static final Map<String, String> generateCommonParams() {
        Map<String, String> ret = new HashMap<>();
        //app版本（纯数字类型，例如=223344），客户端升级所需
        ret.put("appVersion", AppUtils.getAppVersionCode() + "");
        //用户设备id
        ret.put("deviceId", DeviceUtils.getAndroidID());
        //设备制造商
        ret.put("deviceName", DeviceUtils.getManufacturer());
        //设备型号
        ret.put("deviceManuid", DeviceUtils.getModel());
        //设备系统版本 例如Android 6.0
        ret.put("deviceOs", DeviceUtils.getSDKVersionCode() + "");
        //客户端来源 4=android 6=h5 7=web 8=ios原生 11=windows客户端
        ret.put("srcCode", "4");
        return ret;
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }


    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder();

                // 请求头
                Headers.Builder headerBuilder = request.headers().newBuilder();

                Author author = PersistentAuthor.getInstance().loadAuthor();
                if (author != null) {
                    headerBuilder.add(author.getKey(), author.getValue());
                }

                if (headerParamsMap.size() > 0) {
                    for (Map.Entry entry : headerParamsMap.entrySet()) {
                        headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
                    }
                }

                // GET queryParams
                if ("GET".equals(request.method())) {
                    HttpUrl.Builder httpUrl = request.url().newBuilder();
                    if (paramsMap.size() > 0) {
                        for (Map.Entry entry : paramsMap.entrySet()) {
                            httpUrl.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
                        }
                    }
                    requestBuilder.url(httpUrl.build());
                    requestBuilder.headers(headerBuilder.build());
                    request = requestBuilder.build();
                }

                // POST bodyParams
                if ("POST".equals(request.method())) {
                    if (request.body() instanceof FormBody) {
                        FormBody.Builder newFormBodyBuilder = new FormBody.Builder();
                        if (paramsMap.size() > 0) {
                            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                                newFormBodyBuilder.add(entry.getKey(), entry.getValue());
                            }
                        }

                        FormBody oldFormBody = (FormBody) request.body();
                        for (int i = 0; i < oldFormBody.size(); i++) {
                            newFormBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
                        }

                        requestBuilder.post(newFormBodyBuilder.build());
                        requestBuilder.headers(headerBuilder.build());
                        request = requestBuilder.build();
                    } else if (request.body() instanceof MultipartBody) {
                        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                        if (paramsMap.size() > 0) {
                            for (Map.Entry entry : paramsMap.entrySet()) {
                                multipartBuilder.addFormDataPart((String) entry.getKey(), (String) entry.getValue());
                            }
                        }
                        List<MultipartBody.Part> oldParts = ((MultipartBody) request.body()).parts();
                        if (oldParts != null && oldParts.size() > 0) {
                            for (MultipartBody.Part part : oldParts) {
                                multipartBuilder.addPart(part);
                            }
                        }

                        requestBuilder.post(multipartBuilder.build());
                        requestBuilder.headers(headerBuilder.build());
                        request = requestBuilder.build();
                    }

                }
                return chain.proceed(request);
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
        return "http://yyyy.yyyyyyy.com/";
    }

    @Override
    public String getTest() {
        Toast.makeText(BaseNetworkApi.getApplication(), "测试环境", Toast.LENGTH_SHORT).show();
        return "http://xxx.xxxxxx.com/";
    }
}
