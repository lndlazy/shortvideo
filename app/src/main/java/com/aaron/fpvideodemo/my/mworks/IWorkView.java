package com.aaron.fpvideodemo.my.mworks;

import com.aaron.fpvideodemo.base.BaseView;

import org.json.JSONArray;

/**
 * Created by linaidao on 2019/5/4.
 */

public interface IWorkView extends BaseView {


    void updateInfo(MyVideoEntry my_ugc_list, boolean isRefush);
}
