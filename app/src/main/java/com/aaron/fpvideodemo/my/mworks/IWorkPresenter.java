package com.aaron.fpvideodemo.my.mworks;

import com.aaron.fpvideodemo.base.Basebean;
import com.aaron.fpvideodemo.common.Constant;
import com.aaron.fpvideodemo.login.TCUserMgr;
import com.aaron.fpvideodemo.net.BaseNoTObserver;
import com.aaron.fpvideodemo.net.RetrofitHttpUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by linaidao on 2019/5/4.
 */

public class IWorkPresenter {

    private IWorkView iWorkView;

    public IWorkPresenter(IWorkView iWorkView) {
        this.iWorkView = iWorkView;
    }

    //获取我的作品列表
    public void getMyWorks(String userId, int page, String keyWord, final boolean refush) {

        try {

            RetrofitHttpUtil.getInstance().myUgcList(new BaseNoTObserver<MyVideoEntry>() {
                @Override
                public void onHandleSuccess(MyVideoEntry myVideoEntry) {

                    //更新数据，
                    iWorkView.updateInfo(myVideoEntry, refush);

                }

                @Override
                public void onHandleError(String message) {

                }

            }, userId, page, Constant.PIGE_SIZE, keyWord);


//            JSONObject body = new JSONObject().put("userId", Constant.userId)
//                    .put("page", page)
//                    .put("size", Constant.PIGE_SIZE)
//                    .put("keyWord", "");
//            TCUserMgr.getInstance().request("/myUgcList", body, new TCUserMgr.HttpCallback("my_ugc_list", new TCUserMgr.Callback() {
//                @Override
//                public void onSuccess(JSONObject data) {
//
//                    Logger.d("我的作品::" + data.toString());
//
//                    MyVideoEntry myVideoEntry = com.alibaba.fastjson.JSONObject.parseObject(data.toString(), MyVideoEntry.class);
////                        JSONArray my_ugc_list = data.getJSONArray("my_ugc_list");
//                    //更新数据，
//                    iWorkView.updateInfo(myVideoEntry, true);
//                }
//
//                @Override
//                public void onFailure(int code, String msg) {
//
//                }
//            }));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
