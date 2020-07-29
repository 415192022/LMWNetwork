package com.lmw.lmwnetwork.lib.progress;

public interface  ProgressListener {

    void onProgress(long currentBytes, long contentLength, boolean done);

}