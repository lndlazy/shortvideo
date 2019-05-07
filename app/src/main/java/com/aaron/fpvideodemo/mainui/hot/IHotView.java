package com.aaron.fpvideodemo.mainui.hot;

import com.aaron.fpvideodemo.base.BaseView;
import com.aaron.fpvideodemo.mainui.list.VideoListEntry;

/**
 * Created by linaidao on 2019/5/4.
 */

public interface IHotView extends BaseView {


    void updateInfo(boolean refush, VideoListEntry.DataBean videoListInfo);

}
