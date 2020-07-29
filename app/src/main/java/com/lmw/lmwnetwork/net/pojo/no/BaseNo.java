package com.lmw.lmwnetwork.net.pojo.no;

import com.google.gson.annotations.SerializedName;

public class BaseNo {
    @SerializedName(value = "returnCode", alternate = {"return_code"})
    private int returnCode = -1;
    @SerializedName(value = "returnMessage", alternate = {"return_message"})
    private String returnMessage = "";


    public BaseNo() {
    }

    public BaseNo(int returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
