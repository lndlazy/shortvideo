package com.aaron.fpvideodemo.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aaron.fpvideodemo.R;

/**
 * Created by linaidao on 2019/5/4.
 */

public class IBaseFragment extends Fragment implements BaseView {
    protected ProgressDialog showDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mApplication = (MyApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        outSideHideSoft(container);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    private void outSideHideSoft(ViewGroup container) {
//
//        //点击其他部位隐藏软键盘
//        container.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
//                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    }
//                }
//                return false;
//            }
//        });
//    }

    /**
     * startActivity
     * 跳转到下一个页面
     *
     * @param clazz
     */
    @Override
    public void nextActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity then finish
     * 跳转到下一个页面然后销毁当前页面
     */
    @Override
    public void nextActivityThenKill(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * startActivityForResult
     * 跳转到下一个页面带code
     */
    @Override
    public void nextActivityWithCode(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到下一个页面带值
     */
    @Override
    public void nextActivityWithBundle(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void nextActivityForPhoto(Intent intent, int requestCode) {
//        startActivity(intent, requestCode);
    }

//    @Override
//    public void nextActivityWithSingleTask(Class<?> clazz) {
//        if (getActivity() == null)
//            return;
//
//        if (sp == null)
//            sp = getActivity().getSharedPreferences(Constant.CONFIG, Context.MODE_PRIVATE);
//        sp.edit().putBoolean(SpKey.INTERVAL_CONNET_MQTT, false).apply();
//
//        Intent intent = new Intent(getActivity(), clazz);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.putExtra(SpKey.LOGIN_OUT, true);
//        startActivity(intent);
//    }

    @Override
    public void nextActivityWithIntent(Intent intent) {
        startActivity(intent);
    }

    private void showToash(final String text) {

        try {
            if (!TextUtils.isEmpty(text)) {
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showLoading(String msg) {
        showDialog = ProgressDialog.show(getActivity(), "", msg);
        showDialog.setCancelable(true);
        showDialog.setCanceledOnTouchOutside(false);
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
            getActivity().runOnUiThread(new Runnable() {
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

    public void canCancle() {
        if (showDialog != null)
            showDialog.setCancelable(true);
    }

    public void noCancle() {
        if (showDialog != null)
            showDialog.setCancelable(false);
    }

    public void showDialog(String title, String message, String ok) {

        new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom)
                .setTitle(title).setMessage(message).setPositiveButton(ok, null).show();

    }

}
