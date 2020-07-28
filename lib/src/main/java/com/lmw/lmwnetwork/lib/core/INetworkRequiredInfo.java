package com.lmw.lmwnetwork.lib.core;

import android.app.Application;

public interface INetworkRequiredInfo {
    String getAppVersionName();
    String getAppVersionCode();
    boolean isDebug();
    Application getApplicationContext();
}
