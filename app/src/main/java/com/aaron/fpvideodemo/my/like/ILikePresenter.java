package com.aaron.fpvideodemo.my.like;

import com.aaron.fpvideodemo.base.Basebean;
import com.aaron.fpvideodemo.common.Constant;
import com.aaron.fpvideodemo.net.BaseNoTObserver;
import com.aaron.fpvideodemo.net.RetrofitHttpUtil;

/**
 * Created by linaidao on 2019/5/4.
 */

public class ILikePresenter {

    ILikeView iLikeView;

    public ILikePresenter(ILikeView iLikeView) {
        this.iLikeView = iLikeView;
    }

    /**
     * 我点赞过的视频
     *
     * @param page     当前页面（从1开始）
     * @param keyWords 关键字
     * @param isRefush 是否刷新当前的列表
     */
    public void iLikeList(String userId, int page, String keyWords, final boolean isRefush) {

        RetrofitHttpUtil.getInstance().myLikeList(new BaseNoTObserver<ILikeEntry>() {
            @Override
            public void onHandleSuccess(ILikeEntry iLikeEntry) {

                if (iLikeEntry != null && iLikeEntry.getData() != null)
                    iLikeView.updateLikeList(iLikeEntry.getData(), isRefush);
                else
                    iLikeView.showErr("数据错误");
            }

            @Override
            public void onHandleError(String message) {
                iLikeView.showErr("获取列表失败");
            }

        }, userId, page, Constant.PIGE_SIZE, keyWords);

    }

    /**
     * 点赞操作
     * <p>
     * type 点赞:1,取消点赞:0
     */
    public void videoLikeOperate(final int type, String fileId) {

        RetrofitHttpUtil.getInstance().vodLike(new BaseNoTObserver<Basebean>() {
            @Override
            public void onHandleSuccess(Basebean basebean) {

            }

            @Override
            public void onHandleError(String message) {
                iLikeView.showErr(message);
//                iLikeView.showErr(type == 1 ? "点赞失败" : "点赞成功");
            }

        }, Constant.userId, type, fileId);

    }

    /**
     * @param type 关注:1,取消关注:0
     *             关注用户
     */
    public void attentionUser(int type, String likeUserId, String likeUserImg, String likeUserName) {

        RetrofitHttpUtil.getInstance().userLike(new BaseNoTObserver<Basebean>() {
            @Override
            public void onHandleSuccess(Basebean basebean) {

            }

            @Override
            public void onHandleError(String message) {
                iLikeView.showErr(message);
            }

        }, type, Constant.userId, likeUserId, likeUserImg, likeUserName);

    }

    /**
     * 粉丝数和关注数
     */
    public void fansAndLikeNum(String userId) {

        RetrofitHttpUtil.getInstance().fansAndLikeNum(new BaseNoTObserver<Basebean>() {
            @Override
            public void onHandleSuccess(Basebean basebean) {

            }

            @Override
            public void onHandleError(String message) {

                iLikeView.showErr(message);
            }

        }, userId);

    }


}
