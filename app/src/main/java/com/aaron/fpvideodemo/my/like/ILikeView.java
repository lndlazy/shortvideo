package com.aaron.fpvideodemo.my.like;

import com.aaron.fpvideodemo.base.BaseView;

/**
 * Created by linaidao on 2019/5/4.
 */

public interface ILikeView  extends BaseView{


    void updateLikeList(ILikeEntry.DataBean data, boolean isRefush);
}
