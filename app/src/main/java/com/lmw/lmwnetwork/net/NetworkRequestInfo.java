package com.lmw.lmwnetwork.net;


import android.app.Application;

import com.lmw.lmwnetwork.BuildConfig;
import com.lmw.lmwnetwork.lib.core.INetworkRequiredInfo;

public class NetworkRequestInfo implements INetworkRequiredInfo {
    private Application mApplication;

    public NetworkRequestInfo(Application application) {
        this.mApplication = application;
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return mApplication;
    }

}
