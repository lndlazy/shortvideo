package com.aaron.fpvideodemo.mainui.list;

import org.json.JSONObject;

import java.io.Serializable;

public class TCVideoInfo implements Serializable {
    public String review_status;
    public String userid;
    public String groupid;
    //    public int      timestamp;
    public boolean livePlay;
    public String viewerCount;
    public String likeCount;
    public String title;
    public String playurl;
    public String fileid;
    public String nickname;
    public String headpic;
    public String frontcover;
    public String location;
    public String avatar;
    public String createTime;
    public String startTime;
    public String hlsPlayUrl;
    public boolean isLike;

    public final static String REVIEW_STATUS_NOT_REVIEW = "0";
    public final static String REVIEW_STATUS_NORMAL = "1";
    public final static String REVIEW_STATUS_PORN = "2";

    public TCVideoInfo() {
    }

    public TCVideoInfo(JSONObject data) {
        try {
            if (data.has("review_status")) {
                // 如果后台接入了鉴黄功能，需要根据状态来判断要不要播放
                this.review_status = data.optString("review_status");
            } else {
                // 如果后台没有接入鉴黄功能，视频可以正常播放
                this.review_status = REVIEW_STATUS_NORMAL + "";
            }
            this.userid = data.optString("userId");
            this.nickname = data.optString("userName");
            this.avatar = data.optString("userImg");
            this.fileid = data.optString("fileId");
            this.title = data.optString("title");
            this.frontcover = data.optString("frontCover");
            this.location = data.optString("location");
            this.playurl = data.optString("playUrl");
            this.hlsPlayUrl = data.optString("hls_play_url");
            this.createTime = data.optString("createTime");
            this.likeCount = data.optString("likeCount");
            this.viewerCount = data.optString("viewerCount");
            this.startTime = data.optString("start_time");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
