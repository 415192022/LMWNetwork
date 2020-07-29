package com.lmw.lmwnetwork.net.pojo.bo;


public class UserLoginBo {

    private String userId;

    //登录票据，标号不能修改
    private String token;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

}
