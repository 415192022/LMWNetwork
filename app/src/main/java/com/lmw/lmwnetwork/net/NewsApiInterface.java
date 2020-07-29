package com.lmw.lmwnetwork.net;

import com.lmw.lmwnetwork.net.pojo.bo.NewsChannelsBo;
import com.lmw.lmwnetwork.net.pojo.bo.NewsListBo;
import com.lmw.lmwnetwork.net.pojo.no.BaseNo;
import com.lmw.lmwnetwork.net.pojo.no.UserLoginNo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NewsApiInterface {
    @GET("release/news")
    Observable<NewsListBo> getNewsList(@Query("channelId") String channelId,
                                       @Query("channelName") String channelName,
                                       @Query("page") String page);

    @GET("release/channel")
    Observable<NewsChannelsBo> getNewsChannels();

    @FormUrlEncoded
    @POST("xxx/xxx/xx")
    Observable<BaseNo> creationDelete(@Field("id") String id);

    //短信登录
    @FormUrlEncoded
    @POST("xxx/xxx/xx")
    Observable<UserLoginNo> userSmsLogin(@FieldMap Map<String, String> map);
}
