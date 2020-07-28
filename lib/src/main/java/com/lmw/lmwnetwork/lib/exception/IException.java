package com.lmw.lmwnetwork.lib.exception;

import retrofit2.HttpException;

public interface IException {
    void onHttpException(HttpException httpException, ResponseThrowable ex);

    void onServerException(ServerException resultException, ResponseThrowable ex);

    void onJsonParseException(ResponseThrowable ex);

    void onConnectException(ResponseThrowable ex);

    void onSSLHandshakeException(ResponseThrowable ex);

    void onConnectTimeoutException(ResponseThrowable ex);

    void onSocketTimeoutException(ResponseThrowable ex);

    void onUnKnowException(ResponseThrowable ex);

    ResponseThrowable getResponseThrowable(Throwable e);
}
