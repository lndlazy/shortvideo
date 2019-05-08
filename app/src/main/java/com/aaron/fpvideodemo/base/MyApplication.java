package com.aaron.fpvideodemo.base;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;

/**
 * Created by linaidao on 2019/4/26.
 */

public class MyApplication extends MultiDexApplication {

    private static final String TAG = "FPVIDEO";


    //您从控制台申请的licence url
    String ugcLicenceUrl = "http://license.vod2.myqcloud.com/license/v1/d56ed8258cf9a60b1feb6c5cc9fc442a/TXUgcSDK.licence";
    //您从控制台申请的licence key
    String ugcKey = "b6098babf2c5ed4c3671d45b556c6237";

    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        initLogger(TAG, true);
        Fresco.initialize(this);
        TXLiveBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);


        //设置是否允许 SDK 打印本地 log，SDK 默认会将 log 写到 sdcard 上的 log / tencent / liteav 文件夹下
        TXLiveBase.setConsoleEnabled(true);
        TXLiveBase.setLogLevel(TXLiveConstants.LOG_LEVEL_DEBUG);


        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);

    }


    public static MyApplication getApplication() {
        return instance;
    }

    private void initLogger(String tag, final boolean isShow) {
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .tag(tag) // 全局tag
//                .build();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否显示线程信息，默认 显示
                .methodCount(2)         // 方法栈打印的个数，默认是 2
                .methodOffset(5)        // 设置调用堆栈的函数偏移值，默认是 5
//                .logStrategy(customLog) // 设置log打印策略，默认是 LogCat
                .tag(tag)   // 设置全局TAG，默认是 PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.DEBUG;
                return isShow;
            }
        });

//        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
//        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
