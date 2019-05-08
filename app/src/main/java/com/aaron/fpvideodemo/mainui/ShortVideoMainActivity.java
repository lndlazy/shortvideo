package com.aaron.fpvideodemo.mainui;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.common.utils.FileUtils;
import com.aaron.fpvideodemo.common.utils.TCUtils;
import com.aaron.fpvideodemo.common.widget.ShortVideoDialog;
import com.aaron.fpvideodemo.login.TCUserMgr;
import com.aaron.fpvideodemo.mainui.hot.HotFragment;
import com.aaron.fpvideodemo.mainui.play.VodPlayFragment;
import com.aaron.fpvideodemo.mainui.userinfo.TCUserInfoFragment;
import com.aaron.fpvideodemo.videorecord.draft.RecordDraftInfo;
import com.aaron.fpvideodemo.videorecord.draft.RecordDraftMgr;
import com.aaron.fpvideodemo.videorecord.VideoShootingActivity;
import com.tencent.liteav.basic.log.TXCLog;

import java.util.List;

public class ShortVideoMainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "ShortVideoMainActivity";
    private long mLastClickPubTS = 0;

    private TextView mBtnVideo, mBtnUser;
    private ImageView mBtnSelect;
    private Fragment mCurrentFragment;
    //    private Fragment mTCLiveListFragment, mTCUserInfoFragment;
    private Fragment mPlayFragment, mTCUserInfoFragment;

    private ShortVideoDialog mShortVideoDialog;
    private LinearLayout ll_top_type;

    private TextView tvRecommand;
    private TextView tvHot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_video_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        showVideoFragment();

        if (checkPermission()) return;

        //如果上次有未发布完的视频，则提示用户继续操作
//        checkLastRecordPart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (TextUtils.isEmpty(TCUserMgr.getInstance().getUserToken())) {
            if (TCUtils.isNetworkAvailable(this) && TCUserMgr.getInstance().hasUser()) {
                TCUserMgr.getInstance().autoLogin(null);
            }
        }
    }

    private void initView() {

        ll_top_type = findViewById(R.id.ll_top_type);


        tvRecommand = (TextView) findViewById(R.id.tvRecommand);
        tvHot = (TextView) findViewById(R.id.tvHot);
        tvRecommand.setOnClickListener(this);
        tvHot.setOnClickListener(this);

        mBtnVideo = findViewById(R.id.btn_home_left);
        mBtnSelect = findViewById(R.id.btn_home_select);
        mBtnUser = findViewById(R.id.btn_home_right);

        mBtnUser.setOnClickListener(this);
        mBtnVideo.setOnClickListener(this);
        mBtnSelect.setOnClickListener(this);

    }

    private void showVideoFragment() {

//        if (mTCLiveListFragment == null) {
//            mTCLiveListFragment = new TCLiveListFragment();
//        }
//        showFragment(mTCLiveListFragment, "live_list_fragment");
        mBtnUser.setTextColor(getResources().getColor(R.color.colorGray4));
        mBtnVideo.setTextColor(getResources().getColor(R.color.white));
        if (mPlayFragment == null) {
            mPlayFragment = new VodPlayFragment();
        }
        showFragment(mPlayFragment, "vod_play_fragment");

    }

    private void checkLastRecordPart() {
        final RecordDraftMgr recordDraftMgr = new RecordDraftMgr(this);
        RecordDraftInfo lastDraftInfo = recordDraftMgr.getLastDraftInfo();
        if (lastDraftInfo == null) {
            return;
        }
        final List<RecordDraftInfo.RecordPart> recordPartList = lastDraftInfo.getPartList();
        if (recordPartList != null && recordPartList.size() > 0) {
            TXCLog.i(TAG, "checkLastRecordPart, recordPartList");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alertDialog = builder.setCancelable(false).setMessage(R.string.record_part_exist)
                    .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //TODO 重新编辑
//                            startActivity(new Intent(ShortVideoMainActivity.this, TCVideoRecordActivity.class));
                        }
                    })
                    .setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            recordDraftMgr.deleteLastRecordDraft();
                            for (final RecordDraftInfo.RecordPart recordPart : recordPartList) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FileUtils.deleteFile(recordPart.getPath());
                                    }
                                }).start();
                            }
                        }
                    }).create();
            alertDialog.show();
        }
    }


    private void showFragment(Fragment fragment, String tag) {
        if (fragment == mCurrentFragment) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.contentPanel, fragment, tag);
        } else {
            transaction.show(fragment);
        }
        mCurrentFragment = fragment;
        transaction.commit();
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_home_left://首页
                ll_top_type.setVisibility(View.VISIBLE);
                showVideoFragment();
                break;

            case R.id.btn_home_select://发布视频
                showSelect();
                break;

            case R.id.btn_home_right://我的
                ll_top_type.setVisibility(View.INVISIBLE);
                showUserFragment();
                break;

            case R.id.tvRecommand://选择推荐
                //切换为推荐
                tvRecommand.setTextColor(getResources().getColor(R.color.white));
                tvHot.setTextColor(getResources().getColor(R.color.colorGray3));
                showVideoFragment();
                break;

            case R.id.tvHot://选择热门
                //切换为推荐
                tvRecommand.setTextColor(getResources().getColor(R.color.colorGray3));
                tvHot.setTextColor(getResources().getColor(R.color.white));
                showHotFragment();
                break;

        }

    }

    private void showUserFragment() {

        mBtnVideo.setTextColor(getResources().getColor(R.color.colorGray4));
        mBtnUser.setTextColor(getResources().getColor(R.color.white));
//        mBtnVideo.setBackgroundResource(R.mipmap.ic_home_video_normal);
//        mBtnUser.setBackgroundResource(R.mipmap.ic_user_selected);
        if (mTCUserInfoFragment == null) {
            mTCUserInfoFragment = new TCUserInfoFragment();
        }
        showFragment(mTCUserInfoFragment, "user_fragment");
    }


    HotFragment hotFragment;

    private void showHotFragment() {

        if (hotFragment == null)
            hotFragment = new HotFragment();

        showFragment(hotFragment, "hot_fragment");

    }


    private void showSelect() {

        Intent m = new Intent(this, VideoShootingActivity.class);
        startActivity(m);

//        if (!TCUserMgr.getInstance().hasUser()) {
//            Intent intent = new Intent(ShortVideoMainActivity.this, LoginActivity.class);
//            startActivity(intent);
//        } else {
//            // 防止多次点击
//            if (System.currentTimeMillis() - mLastClickPubTS > 1000) {
//                mLastClickPubTS = System.currentTimeMillis();
//                if (mShortVideoDialog.isAdded())
//                    mShortVideoDialog.dismiss();
//                else
//                    mShortVideoDialog.show(getFragmentManager(), "");
//            }
//        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exitApp();
    }

    private long mPressedTime = 0;

    public void exitApp() {

        long mNowTime = System.currentTimeMillis();//获取第一次按键时间

        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();

            mPressedTime = mNowTime;

        } else {//退出程序
            exit();
        }
    }


    private void exit() {
        finish();
        System.exit(0);
    }


}
