package com.aaron.fpvideodemo.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by linaidao on 2019/5/4.
 */

public class VideoCommonEntry implements Parcelable {

    private String fileId;
    private String userId;
    private String userName;
    private String userImg;
    private String musicId;
    private String musicName;
    private String title;
    private String frontCover;
    private String location;
    private String playUrl;
    private String likeCount;
    private String viewerCount;
    private String createTime;
    private String review_status;
    private String status;
    private String file_id;

    private List<UgcLikeBean> ugc_like;

    public static class UgcLikeBean {
        /**
         * userId : 9527
         * fileId : 5285890788716890537
         * createTime : 2019-05-04T00:29:29.000Z
         * file_id : 5285890788716890537
         */

        private String userId;
        private String fileId;
        private String createTime;
        private String file_id;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFile_id() {
            return file_id;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrontCover() {
        return frontCover;
    }

    public void setFrontCover(String frontCover) {
        this.frontCover = frontCover;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getViewerCount() {
        return viewerCount;
    }

    public void setViewerCount(String viewerCount) {
        this.viewerCount = viewerCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReview_status() {
        return review_status;
    }

    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileId);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userImg);
        dest.writeString(this.musicId);
        dest.writeString(this.musicName);
        dest.writeString(this.title);
        dest.writeString(this.frontCover);
        dest.writeString(this.location);
        dest.writeString(this.playUrl);
        dest.writeString(this.likeCount);
        dest.writeString(this.viewerCount);
        dest.writeString(this.createTime);
        dest.writeString(this.review_status);
        dest.writeString(this.status);
        dest.writeString(this.file_id);
    }

    public VideoCommonEntry() {
    }

    protected VideoCommonEntry(Parcel in) {
        this.fileId = in.readString();
        this.userId = in.readString();
        this.userName = in.readString();
        this.userImg = in.readString();
        this.musicId = in.readString();
        this.musicName = in.readString();
        this.title = in.readString();
        this.frontCover = in.readString();
        this.location = in.readString();
        this.playUrl = in.readString();
        this.likeCount = in.readString();
        this.viewerCount = in.readString();
        this.createTime = in.readString();
        this.review_status = in.readString();
        this.status = in.readString();
        this.file_id = in.readString();
    }

    public static final Parcelable.Creator<VideoCommonEntry> CREATOR = new Parcelable.Creator<VideoCommonEntry>() {
        @Override
        public VideoCommonEntry createFromParcel(Parcel source) {
            return new VideoCommonEntry(source);
        }

        @Override
        public VideoCommonEntry[] newArray(int size) {
            return new VideoCommonEntry[size];
        }
    };
}
