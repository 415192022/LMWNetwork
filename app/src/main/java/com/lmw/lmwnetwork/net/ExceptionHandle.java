package com.lmw.lmwnetwork.net;

import com.lmw.lmwnetwork.lib.exception.ErrorCode;
import com.lmw.lmwnetwork.lib.exception.ResponseThrowable;
import com.lmw.lmwnetwork.lib.exception.ServerException;
import com.lmw.lmwnetwork.lib.exception.BaseExceptionHandle;

import retrofit2.HttpException;

public class ExceptionHandle extends BaseExceptionHandle {

    @Override
    public void onServerException(ServerException resultException, ResponseThrowable ex) {
        ex.message = resultException.message;
    }

    @Override
    public void onJsonParseException(ResponseThrowable ex) {
        ex.message = "解析错误";
    }

    @Override
    public void onConnectException(ResponseThrowable ex) {
        ex.message = "连接失败";
    }

    @Override
    public void onSSLHandshakeException(ResponseThrowable ex) {
        ex.message = "证书验证失败";
    }

    @Override
    public void onConnectTimeoutException(ResponseThrowable ex) {
        ex.message = "连接超时";
    }

    @Override
    public void onSocketTimeoutException(ResponseThrowable ex) {
        ex.message = "Socket连接超时";
    }

    @Override
    public void onUnKnowException(ResponseThrowable ex) {
        ex.message = "未知错误";
    }

    @Override
    public void onHttpException(HttpException httpException, ResponseThrowable ex) {
        switch (httpException.code()) {
            case ErrorCode.UNAUTHORIZED:
            case ErrorCode.FORBIDDEN:
            case ErrorCode.NOT_FOUND:
            case ErrorCode.REQUEST_TIMEOUT:
            case ErrorCode.GATEWAY_TIMEOUT:
            case ErrorCode.INTERNAL_SERVER_ERROR:
            case ErrorCode.BAD_GATEWAY:
            case ErrorCode.SERVICE_UNAVAILABLE:
            default:
                ex.message = "网络错误";
                break;
        }
    }

    @Override
    public ResponseThrowable getResponseThrowable(Throwable e) {
        return new ResponseThrowable(e);
    }
}

