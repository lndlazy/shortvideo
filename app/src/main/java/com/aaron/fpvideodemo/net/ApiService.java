package com.aaron.fpvideodemo.net;


import com.aaron.fpvideodemo.base.Basebean;
import com.aaron.fpvideodemo.mainui.list.VideoListEntry;
import com.aaron.fpvideodemo.my.like.ILikeEntry;
import com.aaron.fpvideodemo.my.like.LikeEntry;
import com.aaron.fpvideodemo.my.mworks.MyVideoEntry;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 请求的接口
 * Created by Aaron on 17/2/14.
 */
public interface ApiService {

        String APP_URL_NORMAL = "http://47.96.250.148:8110" + "/v1" + "/api";
//    String APP_URL_NORMAL = "http://app1.culturedc.cn/api" + "/v1" + "/encrypt";
    //    String APP_URL = "http://47.96.250.148:8110" + "/v1" + "/api/";
    String APP_URL = "http://app1.culturedc.cn/api" + "/v1" + "/encrypt/";


    //我的作品列表
    @FormUrlEncoded
    @POST("myUgcList")
    Observable<MyVideoEntry> myUgcList(@Field("userId") String userId, @Field("page") int page
            , @Field("size") int size, @Field("keyWord") String keyWord);

    //点赞＆取消点赞
    @FormUrlEncoded
    @POST("vodLike")
    Observable<Basebean> vodLike(@Field("userId") String userId, @Field("type") int type
            , @Field("fileId") String fileId);

    //我点赞过的视频
    @FormUrlEncoded
    @POST("myLikeVodList")
    Observable<ILikeEntry> myLikeList(@Field("userId") String userId, @Field("page") int page
            , @Field("size") int size, @Field("keyWord") String keyWord);

    //关注用户
    @FormUrlEncoded
    @POST("userLike")
    Observable<Basebean> userLike(@Field("type") int type, @Field("userId") String userId, @Field("likeUserId") String likeUserId
            , @Field("likeUserImg") String likeUserImg, @Field("likeUserName") String likeUserName);

    //增加浏览次数
    @FormUrlEncoded
    @POST("vodView")
    Observable<Basebean> vodView(@Field("times") int times, @Field("fileId") String fileId);

    //user和vod是否是我关注和点赞过
    @FormUrlEncoded
    @POST("isLikeUserVod")
    Observable<LikeEntry> isLikeUserVod(@Field("userId") String userId, @Field("likeFileId") String likeFileId
            , @Field("likeUserId") String likeUserId);

    //短视频列表
    @FormUrlEncoded
    @POST("videoList")
    Observable<VideoListEntry> videoList(@Field("type") int type, @Field("page") int page, @Field("size") int size
            , @Field("keyWord") String keyWord);


    //我关注的用户列表
    @FormUrlEncoded
    @POST("myLikeUserList")
    Observable<Basebean> myLikeUserList(@Field("userId") String userId, @Field("page") int page, @Field("size") int size
            , @Field("keyWord") String keyWord);

    //粉丝和关注数
    @FormUrlEncoded
    @POST("fansAndLikeNum")
    Observable<Basebean> fansAndLikeNum(@Field("userId") String userId);


//    //根据城市名获取地理位置
//    @GET("/geocoder")
//    Observable<AddBean> recodeAddress(@Query("address") String address
//            , @Query("output") String output, @Query("key") String key, @Query("city") String city);
//
//    //登录
//    @FormUrlEncoded
//    @POST("/api/app/patient/login")
//    Observable<LoginEntry> loginIn(@Field("ch") String ch, @Field("name") String name, @Field("pwd") String pwd);


}
