package com.aaron.fpvideodemo.mainui;

import com.aaron.fpvideodemo.base.VideoCommonEntry;
import com.aaron.fpvideodemo.mainui.list.TCVideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linaidao on 2019/5/5.
 */

public class TransformUtils {


    public static List<TCVideoInfo> getTransformInfo(List<VideoCommonEntry> my_ugc_list) {

        List<TCVideoInfo> mVideoList = new ArrayList<>();

        if (my_ugc_list == null || my_ugc_list.size() == 0)
            return mVideoList;

        for (int i = 0; i < my_ugc_list.size(); i++) {

            TCVideoInfo videoInfo = new TCVideoInfo();


            videoInfo.review_status =  my_ugc_list.get(i).getReview_status();
            videoInfo.userid = my_ugc_list.get(i).getUserId();
//            videoInfo.groupid = my_ugc_list.get(i).;
            videoInfo.viewerCount =  my_ugc_list.get(i).getViewerCount();
            videoInfo.likeCount =  my_ugc_list.get(i).getLikeCount();

            videoInfo.title = my_ugc_list.get(i).getTitle();
            videoInfo.playurl = my_ugc_list.get(i).getPlayUrl();
            videoInfo.fileid = my_ugc_list.get(i).getFileId();
            videoInfo.nickname = my_ugc_list.get(i).getUserName();
            videoInfo.headpic = my_ugc_list.get(i).getUserImg();
            videoInfo.frontcover = my_ugc_list.get(i).getFrontCover();
            videoInfo.location = my_ugc_list.get(i).getLocation();
            videoInfo.avatar = my_ugc_list.get(i).getUserImg();
            videoInfo.createTime = my_ugc_list.get(i).getCreateTime();
//            videoInfo.startTime = my_ugc_list.get(i).getst();
//            videoInfo.hlsPlayUrl = my_ugc_list.get(i).geth();
//            videoInfo.isLike = my_ugc_list.get(i).();
            mVideoList.add(videoInfo);
        }

        return mVideoList;
    }

}
