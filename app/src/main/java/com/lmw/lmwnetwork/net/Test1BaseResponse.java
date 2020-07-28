package com.lmw.lmwnetwork.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test1BaseResponse {
    @SerializedName("showapi_res_code")
    @Expose
    public Integer showapiResCode;
    @SerializedName("showapi_res_error")
    @Expose
    public String showapiResError;
}
