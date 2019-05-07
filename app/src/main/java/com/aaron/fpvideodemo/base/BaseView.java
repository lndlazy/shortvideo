package com.aaron.fpvideodemo.base;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Aaron on 17/3/28.
 */

public interface BaseView {

    void showLoading(String msg);

    void hideLoading();

    void showErr(String msg);

    void nextActivity(Class<?> clazz);

    void nextActivityThenKill(Class<?> clazz);

    void nextActivityWithCode(Class<?> clazz, int requestCode);

    void nextActivityWithBundle(Class<?> clazz, Bundle bundle);

    void nextActivityForPhoto(Intent intent, int requestCode);

    void nextActivityWithIntent(Intent intent);

//    void nextActivityWithSingleTask(Class<?> clazz);

//    SharedPreferences getSp();
}
