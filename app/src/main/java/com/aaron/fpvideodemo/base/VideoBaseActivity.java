package com.aaron.fpvideodemo.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.aaron.fpvideodemo.R;


/**
 * 基类activity
 * Created by Aaron on 17/3/21.
 */
@SuppressLint("Registered")
public class VideoBaseActivity extends FragmentActivity implements BaseView {

//    protected SharedPreferences sp;
    protected ProgressDialog showDialog;
    protected Bundle bundle;
    protected MyApplication mapplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        sp = getSharedPreferences(SaveUtils.CONFIG, MODE_PRIVATE);
        bundle = getIntent().getBundleExtra("bundle");
        mapplication = (MyApplication) getApplication();

//        BaseActivityPermissionsDispatcher.needPermissionWithPermissionCheck(this);

    }

//    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS})
//    void needPermission() {
//    }
//
//    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS})
//    void permissionDeniedForIO() {
//        showErr("无读写权限");
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * startActivity
     * 跳转到下一个页面
     *
     * @param clazz
     */
    @Override
    public void nextActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity then finish
     * 跳转到下一个页面然后销毁当前页面
     */
    @Override
    public void nextActivityThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     * 跳转到下一个页面带code
     */
    @Override
    public void nextActivityWithCode(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 图片处理Intent
     *
     * @param intent
     * @param requestCode
     */
    public void nextActivityForPhoto(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void nextActivityWithIntent(Intent intent) {
        startActivity(intent);
    }

//    @Override
//    public void nextActivityWithSingleTask(Class<?> clazz) {
//        if (sp == null)
//            sp = getSharedPreferences(Constant.CONFIG, MODE_PRIVATE);
//        sp.edit().putBoolean(SpKey.INTERVAL_CONNET_MQTT, false).apply();
//
//        Intent intent = new Intent(this, clazz);
////        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(SpKey.LOGIN_OUT, true);
//        startActivity(intent);
//
//    }

    /**
     * 跳转到下一个页面带值
     */
    @Override
    public void nextActivityWithBundle(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    private void showToash(final String text) {

        try {
            if (!TextUtils.isEmpty(text))
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void showLoading(String msg) {
        showDialog = ProgressDialog.show(this, "", msg);
        showDialog.setCancelable(true);
        showDialog.setCanceledOnTouchOutside(false);
//        showAlertInfo.setCancelable(true);
//        Log.e("baseavtivity", " 显示 loading ： " + msg);
    }

    @Override
    public void hideLoading() {
        if (showDialog != null && showDialog.isShowing())
            showDialog.dismiss();
    }

    @Override
    public void showErr(final String msg) {

        if (Thread.currentThread() == Looper.getMainLooper().getThread())
            // 如果在主线程中
            showToash(msg);
        else
            // 在子线程中
            runOnUiThread(new Runnable() {
                public void run() {
                    showToash(msg);
                }
            });
    }

//    public String getToken() {
//        return sp.getString(Constant.TOKEN, "");
//    }

//    public SharedPreferences getSp() {
//        return sp;
//    }

    public void showAlertInfo(String title, String message, String ok) {
        new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setTitle(title).setMessage(message).setPositiveButton(ok, null).show();
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

}
