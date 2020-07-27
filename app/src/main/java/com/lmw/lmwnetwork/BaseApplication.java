package com.lmw.lmwnetwork;

import android.app.Application;

import com.lmw.lmwnetwork.lib.base.NetworkApi;
import com.lmw.lmwnetwork.net.NetworkRequestInfo;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNetwork();
    }

    private void initNetwork() {
        NetworkApi.init(new NetworkRequestInfo(this));
    }

}
