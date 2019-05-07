package com.aaron.fpvideodemo.base;

import android.os.Parcel;
import android.os.Parcelable;

public class Basebean implements Parcelable {

    private int code;
    private String code_msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCode_msg() {
        return code_msg;
    }

    public void setCode_msg(String code_msg) {
        this.code_msg = code_msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.code_msg);
    }

    public Basebean() {
    }

    protected Basebean(Parcel in) {
        this.code = in.readInt();
        this.code_msg = in.readString();
    }

    public static final Parcelable.Creator<Basebean> CREATOR = new Parcelable.Creator<Basebean>() {
        @Override
        public Basebean createFromParcel(Parcel source) {
            return new Basebean(source);
        }

        @Override
        public Basebean[] newArray(int size) {
            return new Basebean[size];
        }
    };
}
