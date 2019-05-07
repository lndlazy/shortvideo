package com.aaron.fpvideodemo.my.mworks;

import com.aaron.fpvideodemo.base.Basebean;
import com.aaron.fpvideodemo.base.VideoCommonEntry;

import java.util.List;

/**
 * Created by linaidao on 2019/5/4.
 */

public class MyVideoEntry extends Basebean {

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
        private List<VideoCommonEntry> my_ugc_list;

        public List<VideoCommonEntry> getMy_ugc_list() {
            return my_ugc_list;
        }

        public void setMy_ugc_list(List<VideoCommonEntry> my_ugc_list) {
            this.my_ugc_list = my_ugc_list;
        }


    }
}
