package com.aaron.fpvideodemo.mainui.list;

import com.aaron.fpvideodemo.base.Basebean;
import com.aaron.fpvideodemo.base.VideoCommonEntry;

import java.util.List;

/**
 * Created by linaidao on 2019/5/5.
 */

public class VideoListEntry extends Basebean {


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

        private List<VideoCommonEntry> video_list;

        public List<VideoCommonEntry> getVideo_list() {
            return video_list;
        }

        public void setVideo_list(List<VideoCommonEntry> video_list) {
            this.video_list = video_list;
        }

    }
}
