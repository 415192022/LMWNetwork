package com.lmw.lmwnetwork.net.base.observer;

import com.lmw.lmwnetwork.lib.core.BaseObserver;
import com.lmw.lmwnetwork.net.pojo.no.Test1BaseResponseNo;

import io.reactivex.disposables.Disposable;

public abstract class TencentObserver<T extends Test1BaseResponseNo> extends BaseObserver<T> {
    @Override
    public void onError(Throwable e) {
        onFail(null, e);
        if (e != null) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {
        try {
            if (t != null && t.showapiResCode == 0) {
                onSuccess(t);
            } else {
                onFail(t, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onComplete() {
    }

    abstract public void onSuccess(T t);

    abstract public void onFail(T t, Throwable e);
}
