package com.lmw.lmwnetwork.lib.exception;

import androidx.annotation.Nullable;

public class ResponseThrowable extends Exception {
    public int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String message;

    public ResponseThrowable(Throwable throwable) {
        super(throwable);
    }

}