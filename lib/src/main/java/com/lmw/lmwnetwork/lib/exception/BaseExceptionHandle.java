package com.lmw.lmwnetwork.lib.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

public abstract class BaseExceptionHandle implements IException {

    public ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = getResponseThrowable(e);
            ex.setCode(ErrorCode.HTTP_ERROR);
            onHttpException(httpException, ex);
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = getResponseThrowable(e);
            ex.setCode(resultException.code);
            onServerException(resultException, ex);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = getResponseThrowable(e);
            ex.setCode(ErrorCode.PARSE_ERROR);
            onJsonParseException(ex);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = getResponseThrowable(e);
            ex.setCode(ErrorCode.NETWORD_ERROR);
            onConnectException(ex);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = getResponseThrowable(e);
            ex.setCode(ErrorCode.SSL_ERROR);
            onSSLHandshakeException(ex);
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = getResponseThrowable(e);
            ex.setCode(ErrorCode.TIMEOUT_ERROR);
            onConnectTimeoutException(ex);
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = getResponseThrowable(e);
            ex.setCode(ErrorCode.TIMEOUT_ERROR);
            onSocketTimeoutException(ex);
            return ex;
        } else {
            ex = getResponseThrowable(e);
            ex.setCode(ErrorCode.UNKNOWN);
            onUnKnowException(ex);
            return ex;
        }
    }

    @Override
    public void onHttpException(HttpException httpException, ResponseThrowable ex) {

    }

    @Override
    public void onServerException(ServerException resultException, ResponseThrowable ex) {

    }

    @Override
    public void onJsonParseException(ResponseThrowable ex) {

    }

    @Override
    public void onConnectException(ResponseThrowable ex) {

    }

    @Override
    public void onSSLHandshakeException(ResponseThrowable ex) {

    }

    @Override
    public void onConnectTimeoutException(ResponseThrowable ex) {

    }

    @Override
    public void onSocketTimeoutException(ResponseThrowable ex) {

    }

    @Override
    public void onUnKnowException(ResponseThrowable ex) {

    }

    @Override
    public ResponseThrowable getResponseThrowable(Throwable e) {
        return null;
    }

}
