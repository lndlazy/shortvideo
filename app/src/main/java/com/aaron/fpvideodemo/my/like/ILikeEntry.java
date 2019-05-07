package com.aaron.fpvideodemo.my.like;

import com.aaron.fpvideodemo.base.Basebean;
import com.aaron.fpvideodemo.base.VideoCommonEntry;

import java.util.List;

/**
 * Created by linaidao on 2019/5/4.
 */

public class ILikeEntry extends Basebean {

    /**
     * time : 1556958582720
     * data : {"my_like_vod_list":[{"fileId":"5285890788716890537","userId":"9527","userName":"工号9527","userImg":"http://img.52z.com/upload/news/image/20180823/20180823122912_66977.jpg","musicId":"","musicName":"","title":"小视频","frontCover":"http://1252027113.vod2.myqcloud.com/4687cbbavodcq1252027113/5847787f5285890788716890537/5285890788716890538.jpg","location":null,"playUrl":"http://1252027113.vod2.myqcloud.com/4687cbbavodcq1252027113/5847787f5285890788716890537/55ws9vmojrsA.mp4","likeCount":1,"viewerCount":0,"createTime":"2019-05-03T21:26:06.000Z","review_status":1,"status":1,"file_id":"5285890788716890537","ugc_like":[{"userId":"9527","fileId":"5285890788716890537","createTime":"2019-05-04T00:29:29.000Z","file_id":"5285890788716890537"}]}]}
     */

    private long time;
    private DataBean data;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<VideoCommonEntry> my_like_vod_list;

        public List<VideoCommonEntry> getMy_like_vod_list() {
            return my_like_vod_list;
        }

        public void setMy_like_vod_list(List<VideoCommonEntry> my_like_vod_list) {
            this.my_like_vod_list = my_like_vod_list;
        }

    }
}
