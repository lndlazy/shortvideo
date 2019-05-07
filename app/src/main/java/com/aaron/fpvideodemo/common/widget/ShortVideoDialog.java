package com.aaron.fpvideodemo.common.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.fpvideodemo.R;

/**
 * Created by linaidao on 2019/4/26.
 */

public class ShortVideoDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_short_video);
        dialog.setCanceledOnTouchOutside(true);

//        mTVVideo = (TextView) dialog.findViewById(R.id.tv_record);
//        mTVEditer = (TextView) dialog.findViewById(R.id.tv_editer);
//        mTVPicture = (TextView) dialog.findViewById(R.id.tv_picture);
//        mTVChorus = (TextView) dialog.findViewById(R.id.tv_chorus);
//        mIVClose = (ImageView) dialog.findViewById(R.id.iv_close);
//
//        mTVVideo.setOnClickListener(this);
//        mTVEditer.setOnClickListener(this);
//        mTVPicture.setOnClickListener(this);
//        mTVChorus.setOnClickListener(this);
//        mIVClose.setOnClickListener(this);
//
//        // 设置宽度为屏宽, 靠近屏幕底部。
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.BOTTOM;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        window.setAttributes(lp);
//
//        mDownloadProgressDialog = new ProgressDialog(getActivity());
//        mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置进度条的形式为圆形转动的进度条
//        mDownloadProgressDialog.setCancelable(false);                           // 设置是否可以通过点击Back键取消
//        mDownloadProgressDialog.setCanceledOnTouchOutside(false);               // 设置在点击Dialog外是否取消Dialog进度条

        return dialog;
    }
}
