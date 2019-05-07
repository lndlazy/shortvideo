package com.aaron.fpvideodemo.my.like;

import com.aaron.fpvideodemo.base.Basebean;

/**
 * Created by linaidao on 2019/5/6.
 */

public class LikeEntry extends Basebean {
    /**
     * time : 1557134147572
     * data : {"isMyLikeVod":0,"isMyLikeUser":1}
     */

    private long time;
    private LikeInfoEntry data;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public LikeInfoEntry getData() {
        return data;
    }

    public void setData(LikeInfoEntry data) {
        this.data = data;
    }



//    private long time;
//    private LikeInfoEntry data;
//
//    public long getTime() {
//        return time;
//    }
//
//    public void setTime(long time) {
//        this.time = time;
//    }
//
//    public LikeInfoEntry getLikeInfoEntry() {
//        return data;
//    }
//
//    public void setLikeInfoEntry(LikeInfoEntry data) {
//        this.data = data;
//    }



}
