package com.lmw.lmwnetwork.lib.interceptor;

import android.widget.Toast;

import com.lmw.lmwnetwork.lib.core.BaseNetworkApi;
import com.lmw.lmwnetwork.lib.exception.ResponseThrowable;
import com.lmw.lmwnetwork.lib.exception.BaseExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;

public abstract class BaseInterceptor implements Interceptor {

    /**
     * HttpResponseFunc处理以下两类网络错误：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；
     * 2、应用数据的错误会抛RuntimeException，最后也会走到这个函数来统一处理；
     */
    public static class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
        private BaseExceptionHandle mIExceptionHandle;

        public HttpErrorHandler(BaseExceptionHandle exceptionHandle) {
            mIExceptionHandle = exceptionHandle;
        }

        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            ResponseThrowable responseThrowable = mIExceptionHandle.handleException(throwable);
            Toast.makeText(BaseNetworkApi.getApplication(), "" + responseThrowable.message, Toast.LENGTH_SHORT).show();
            return Observable.error(responseThrowable);
        }
    }
}
