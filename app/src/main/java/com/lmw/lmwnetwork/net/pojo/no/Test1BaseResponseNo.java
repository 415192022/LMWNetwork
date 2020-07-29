package com.lmw.lmwnetwork.net.pojo.no;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test1BaseResponseNo {
    @SerializedName("showapi_res_code")
    @Expose
    public int showapiResCode;
    @SerializedName("showapi_res_error")
    @Expose
    public String showapiResError;
}
