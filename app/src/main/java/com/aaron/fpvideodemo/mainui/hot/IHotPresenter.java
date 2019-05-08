package com.aaron.fpvideodemo.mainui.hot;

import com.aaron.fpvideodemo.common.VideoConstant;
import com.aaron.fpvideodemo.mainui.list.VideoListEntry;
import com.aaron.fpvideodemo.net.BaseNoTObserver;
import com.aaron.fpvideodemo.net.RetrofitHttpUtil;

/**
 * Created by linaidao on 2019/5/4.
 */

public class IHotPresenter {

    private IHotView iHotView;

    public IHotPresenter(IHotView iHotView) {
        this.iHotView = iHotView;
    }

    public void hotList(int page, String keyWord, final boolean refush) {

        RetrofitHttpUtil.getInstance().videoList(new BaseNoTObserver<VideoListEntry>() {
            @Override
            public void onHandleSuccess(VideoListEntry videoListEntry) {

                if (videoListEntry != null && videoListEntry.getData() != null) {

                    iHotView.updateInfo(refush, videoListEntry.getData());

                } else {
                    iHotView.showErr("数据错误");
                }

            }

            @Override
            public void onHandleError(String message) {

                iHotView.showErr("数据加载失败");
            }

        }, 2, page, VideoConstant.PIGE_SIZE, keyWord);

    }


}
