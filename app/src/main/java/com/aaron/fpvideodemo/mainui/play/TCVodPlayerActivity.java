package com.aaron.fpvideodemo.mainui.play;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.common.activity.TCBaseActivity;
import com.aaron.fpvideodemo.common.utils.DownloadUtil;
import com.aaron.fpvideodemo.common.utils.TCConstants;
import com.aaron.fpvideodemo.common.utils.TCUtils;
import com.aaron.fpvideodemo.login.LoginActivity;
import com.aaron.fpvideodemo.login.TCUserMgr;
import com.aaron.fpvideodemo.mainui.list.TCLiveListFragment;
import com.aaron.fpvideodemo.mainui.list.TCVideoInfo;
import com.aaron.fpvideodemo.mainui.search.VideoSearchActivity;
import com.aaron.fpvideodemo.my.MyActivity;
import com.aaron.fpvideodemo.videorecord.VideoShootingActivity;
import com.bumptech.glide.Glide;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoInfoReader;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 视频流瀑布流列表页面
 * Created by linaidao on 2019/4/26.
 */

public class TCVodPlayerActivity extends TCBaseActivity implements ITXVodPlayListener {

    private static final String TAG = "TCVodPlayerActivity";

    private VerticalViewPager mVerticalViewPager;

    private MyPagerAdapter mPagerAdapter;

    // 发布者id 、视频地址、 发布者名称、 头像URL、 封面URL
    private List<TCVideoInfo> mTCLiveInfoList;
    private int mInitTCLiveInfoPosition;

    private TXCloudVideoView mTXCloudVideoView;
    private ImageView mIvCover;
    private TextView mTvBack;
    // 合拍相关
//    private ImageButton mImgBtnFollowShot;
    private TXVideoInfoReader mVideoInfoReader;
    private int mCurrentPosition;
    private ProgressDialog mDownloadProgressDialog;
    /**
     * SDK播放器以及配置
     */
    private TXVodPlayer mTXVodPlayer;

    @Override
    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
            int width = param.getInt(TXLiveConstants.EVT_PARAM1);
            int height = param.getInt(TXLiveConstants.EVT_PARAM2);
            if (width > height) {
                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
            } else {
                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            restartPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {// 视频I帧到达，开始播放

            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null) {
                playerInfo.isBegin = true;
            }
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event I FRAME, player = " + player);
                mIvCover.setVisibility(View.GONE);
                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY, TCUserMgr.getInstance().getUserId(), event, "点播播放成功", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });
            }
        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);
                mTXVodPlayer.resume();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            //如下这段代码是处理播放显示的事件，言下之意：不要转菊花了
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null && playerInfo.isBegin) {
                mIvCover.setVisibility(View.GONE);
                TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
            }
        } else if (event < 0) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);

                String desc = null;
                switch (event) {
                    case TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL:
                        desc = "获取加速拉流地址失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND:
                        desc = "文件不存在";
                        break;
                    case TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL:
                        desc = "h265解码失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_HLS_KEY:
                        desc = "HLS解密key获取失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL:
                        desc = "获取点播文件信息失败";
                        break;
                }
                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY, TCUserMgr.getInstance().getUserId(), event, desc, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG, "onFailure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i(TAG, "onResponse");
                    }
                });
            }
            Toast.makeText(this, "event:" + event, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }


    @Override
    protected void showErrorAndQuit(String errorMsg) {
        mTXCloudVideoView.onPause();
        stopPlay(true);
        Intent rstData = new Intent();
        rstData.putExtra(TCConstants.ACTIVITY_RESULT, errorMsg);
        setResult(TCLiveListFragment.START_LIVE_PLAY, rstData);
        super.showErrorAndQuit(errorMsg);
    }

    class PlayerInfo {
        public TXVodPlayer txVodPlayer;
        public String playURL;
        public boolean isBegin;
        public View playerView;
        public int pos;
        public String reviewstatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_play);
        initDatas();
        initViews();
        initPlayerSDK();
        initPhoneListener();

        //在这里停留，让列表界面卡住几百毫秒，给sdk一点预加载的时间，形成秒开的视觉效果
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void initPhoneListener() {
        if (mPhoneListener == null)
            mPhoneListener = new TXPhoneStateListener(mTXVodPlayer);
        TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }


    private void initDatas() {
        Intent intent = getIntent();
        mTCLiveInfoList = (List<TCVideoInfo>) intent.getSerializableExtra(TCConstants.TCLIVE_INFO_LIST);
        mInitTCLiveInfoPosition = intent.getIntExtra(TCConstants.TCLIVE_INFO_POSITION, 0);
    }

    private void initViews() {
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.player_cloud_view);
        mIvCover = (ImageView) findViewById(R.id.player_iv_cover);
        mTvBack = (TextView) findViewById(R.id.player_tv_back);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mImgBtnFollowShot = (ImageButton) findViewById(R.id.imgBtn_follow_shot);
//        mImgBtnFollowShot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isLogin()) {
//                    return;
//                }
//                if (mVideoInfoReader == null) {
//                    mVideoInfoReader = TXVideoInfoReader.getInstance();
//                }
//                // 上报合唱
//                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VIDEO_CHORUS, TCUserMgr.getInstance().getUserId(), 0, "合唱事件", new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                    }
//                });
//                // 合拍之前先下载视频
//                downloadVideo();
//            }
//        });
        mDownloadProgressDialog = new ProgressDialog(this);
        mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置进度条的形式为圆形转动的进度条
        mDownloadProgressDialog.setCancelable(false);                           // 设置是否可以通过点击Back键取消
        mDownloadProgressDialog.setCanceledOnTouchOutside(false);               // 设置在点击Dialog外是否取消Dialog进度条

        mVerticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        mVerticalViewPager.setOffscreenPageLimit(2);
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                TXLog.i(TAG, "mVerticalViewPager, onPageScrolled position = " + position);
//                mCurrentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                TXLog.i(TAG, "mVerticalViewPager, onPageSelected position = " + position);
                mCurrentPosition = position;
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                TXLog.i(TAG, "滑动后，让之前的播放器暂停，mTXVodPlayer = " + mTXVodPlayer);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(0);
                    mTXVodPlayer.pause();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mVerticalViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                TXLog.i(TAG, "mVerticalViewPager, transformPage pisition = " + position + " mCurrentPosition" + mCurrentPosition);
                if (position != 0) {
                    return;
                }

                ViewGroup viewGroup = (ViewGroup) page;
                mIvCover = (ImageView) viewGroup.findViewById(R.id.player_iv_cover);
                mTXCloudVideoView = (TXCloudVideoView) viewGroup.findViewById(R.id.player_cloud_view);

//                ImageView iv_search = viewGroup.findViewById(R.id.iv_search);

                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    playerInfo.txVodPlayer.resume();
                    mTXVodPlayer = playerInfo.txVodPlayer;
                }

                mTXCloudVideoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mTXVodPlayer != null) {
                            if (mTXVodPlayer.isPlaying()) {
                                mTXVodPlayer.pause();
                            } else {
                                mTXVodPlayer.resume();
                            }
                        }

                    }
                });

            }
        });

        mPagerAdapter = new MyPagerAdapter();
        mVerticalViewPager.setAdapter(mPagerAdapter);

    }

    private void downloadVideo() {
        TCVideoInfo tcVideoInfo = mTCLiveInfoList.get(mCurrentPosition);
        if (tcVideoInfo.review_status == TCVideoInfo.REVIEW_STATUS_NOT_REVIEW) {
            Toast.makeText(this, R.string.tc_ugc_video_list_adapter_video_state_in_audit, Toast.LENGTH_SHORT).show();
            return;
        } else if (tcVideoInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) {
            Toast.makeText(this, R.string.tc_ugc_video_list_adapter_video_state_pornographic, Toast.LENGTH_SHORT).show();
            return;
        }
        mDownloadProgressDialog.show();
        File downloadFileFolder = new File(Environment.getExternalStorageDirectory(), TCConstants.OUTPUT_DIR_NAME);
        File downloadFile = new File(downloadFileFolder, DownloadUtil.getNameFromUrl(tcVideoInfo.playurl));

        if (downloadFile.exists()) {
            mDownloadProgressDialog.dismiss();
            TXVideoEditConstants.TXVideoInfo txVideoInfo = mVideoInfoReader.getVideoFileInfo(downloadFile.getAbsolutePath());
            startRecordActivity(downloadFile.getAbsolutePath(), (int) txVideoInfo.fps, txVideoInfo.audioSampleRate);
            return;
        }
        mDownloadProgressDialog.setMessage(getResources().getString(R.string.tc_vod_player_activity_download_video_is_downloading));

        DownloadUtil.get().download(tcVideoInfo.playurl, TCConstants.OUTPUT_DIR_NAME, new DownloadUtil.DownloadListener() {
            @Override
            public void onDownloadSuccess(final String path) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.dismiss();
                        TXVideoEditConstants.TXVideoInfo txVideoInfo = mVideoInfoReader.getVideoFileInfo(path);
                        startRecordActivity(path, (int) txVideoInfo.fps, txVideoInfo.audioSampleRate);
                    }
                });
            }

            @Override
            public void onDownloading(final int progress) {
                TXCLog.i(TAG, "downloadVideo, progress = " + progress);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.setMessage(
                                getResources().getString(R.string.tc_vod_player_activity_download_video_is_downloading)
                                        + progress + "%");
                    }
                });
            }

            @Override
            public void onDownloadFailed() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.dismiss();
                        Toast.makeText(TCVodPlayerActivity.this,
                                getResources().getString(R.string.tc_vod_player_activity_download_video_download_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean isLogin() {
        if (!TCUserMgr.getInstance().hasUser()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void startRecordActivity(String path, int fps, int audioSampleRate) {
        if (fps <= 0) {
            fps = 20;
        }
        int audioSampleRateType = TXRecordCommon.AUDIO_SAMPLERATE_48000;
        if (audioSampleRate == 8000) {
            audioSampleRateType = TXRecordCommon.AUDIO_SAMPLERATE_8000;
        } else if (audioSampleRate == 16000) {
            audioSampleRateType = TXRecordCommon.AUDIO_SAMPLERATE_16000;
        } else if (audioSampleRate == 32000) {
            audioSampleRateType = TXRecordCommon.AUDIO_SAMPLERATE_32000;
        } else if (audioSampleRate == 44100) {
            audioSampleRateType = TXRecordCommon.AUDIO_SAMPLERATE_44100;
        } else {
            audioSampleRateType = TXRecordCommon.AUDIO_SAMPLERATE_48000;
        }
        Intent intent = new Intent(TCVodPlayerActivity.this, VideoShootingActivity.class);
        intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_FOLLOW_SHOT);
        intent.putExtra(TCConstants.VIDEO_EDITER_PATH, path);
        intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, mTXVodPlayer.getDuration());
        intent.putExtra(TCConstants.VIDEO_RECORD_AUDIO_SAMPLE_RATE_TYPE, audioSampleRateType);
        intent.putExtra(TCConstants.RECORD_CONFIG_FPS, fps);
        startActivity(intent);
    }

    private void restartPlay() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onResume();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.pause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }
        stopPlay(true);
        mTXVodPlayer = null;

        if (mPhoneListener != null) {
            TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);
            mPhoneListener = null;
        }
    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.stopPlay(clearLastFrame);
        }
    }

    private TXVodPlayer vodPlayer;

    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();


        protected PlayerInfo instantiatePlayerInfo(int position) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);

            PlayerInfo playerInfo = new PlayerInfo();
            vodPlayer = new TXVodPlayer(TCVodPlayerActivity.this);
            //RENDER_ROTATION_PORTRAIT - 常规的竖屏显示，如果是显示人像，则最适合这种模式了
            //RENDER_ROTATION_LANDSCAPE - 横屏显示，游戏直播比较适合这种模式
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            //RENDER_MODE_FULL_FILL_SCREEN - 将图像等比例铺满整个屏幕，多余部分裁剪掉，此模式下画面不留黑边
            //RENDER_MODE_ADJUST_RESOLUTION - 将图像等比例缩放，缩放后的宽和高都不会超过显示区域，居中显示，可能会留有黑边
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);

            vodPlayer.setVodListener(TCVodPlayerActivity.this);
            TXVodPlayConfig config = new TXVodPlayConfig();
            config.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/txcache");
            config.setMaxCacheItems(5);
            vodPlayer.setConfig(config);
            vodPlayer.setAutoPlay(false);

//            // 暂停
//            vodPlayer.pause();
//            // 继续
//            vodPlayer.resume();


            TCVideoInfo tcLiveInfo = mTCLiveInfoList.get(position);
            playerInfo.playURL = TextUtils.isEmpty(tcLiveInfo.hlsPlayUrl) ? tcLiveInfo.playurl : tcLiveInfo.hlsPlayUrl;
            playerInfo.txVodPlayer = vodPlayer;
            playerInfo.reviewstatus = tcLiveInfo.review_status;
            playerInfo.pos = position;
            playerInfoList.add(playerInfo);

            return playerInfo;
        }

        protected void destroyPlayerInfo(int position) {
            while (true) {
                PlayerInfo playerInfo = findPlayerInfo(position);
                if (playerInfo == null)
                    break;
                playerInfo.txVodPlayer.stopPlay(true);
                playerInfoList.remove(playerInfo);

                TXCLog.d(TAG, "destroyPlayerInfo " + position);
            }
        }

        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.pos == position) {
                    return playerInfo;
                }
            }
            return null;
        }

        public PlayerInfo findPlayerInfo(TXVodPlayer player) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.txVodPlayer == player) {
                    return playerInfo;
                }
            }
            return null;
        }

        public void onDestroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                playerInfo.txVodPlayer.stopPlay(true);
            }
            playerInfoList.clear();
        }

        @Override
        public int getCount() {
            return mTCLiveInfoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TXCLog.i(TAG, "MyPagerAdapter instantiateItem, position = " + position);
            TCVideoInfo tcLiveInfo = mTCLiveInfoList.get(position);

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content, null);
            view.setId(position);
            // 封面
            ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);
            if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) { //涉黄的图片不显示
                coverImageView.setImageResource(R.mipmap.bg_ugc);
            } else {
                TCUtils.blurBgPic(TCVodPlayerActivity.this, coverImageView, tcLiveInfo.frontcover, R.mipmap.bg_ugc);
            }
            // 头像
            CircleImageView ivAvatar = (CircleImageView) view.findViewById(R.id.player_civ_avatar);

            //搜索按钮
            ImageView iv_search = view.findViewById(R.id.iv_search);
            //加号
            ImageView iv_plus = view.findViewById(R.id.iv_plus);
            //视频介绍
            TextView player_tv_troduce = view.findViewById(R.id.player_tv_troduce);
            //点赞
            ImageView iv_heart_pic = view.findViewById(R.id.iv_heart_pic);
            //点赞数
            TextView tv_heart_count = view.findViewById(R.id.tv_heart_count);
            //分享
            ImageView iv_share_pic = view.findViewById(R.id.iv_share_pic);
            //分享数
            TextView tv_share_count = view.findViewById(R.id.tv_share_count);

            //点击头像，进入我的页面
            ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent m = new Intent(TCVodPlayerActivity.this, MyActivity.class);
                    startActivity(m);
                }
            });

            iv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //搜索
                    Intent m = new Intent(TCVodPlayerActivity.this, VideoSearchActivity.class);
                    startActivity(m);

                }
            });

            Glide.with(TCVodPlayerActivity.this).load(tcLiveInfo.headpic).error(R.mipmap.face).into(ivAvatar);
            // 姓名
            TextView tvName = (TextView) view.findViewById(R.id.player_tv_publisher_name);
            if (TextUtils.isEmpty(tcLiveInfo.nickname) || "null".equals(tcLiveInfo.nickname)) {
                tvName.setText(TCUtils.getLimitString(tcLiveInfo.userid, 10));
            } else {
                tvName.setText(tcLiveInfo.nickname);
            }
            TextView tvVideoReviewStatus = (TextView) view.findViewById(R.id.tx_video_review_status);
            if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_NOT_REVIEW) {
                tvVideoReviewStatus.setVisibility(View.VISIBLE);
                tvVideoReviewStatus.setText(R.string.video_not_review);
            } else if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) {
                tvVideoReviewStatus.setVisibility(View.VISIBLE);
                tvVideoReviewStatus.setText(R.string.video_porn);
            } else if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_NORMAL) {
                tvVideoReviewStatus.setVisibility(View.GONE);
            }

            // 获取此player
            TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
            PlayerInfo playerInfo = instantiatePlayerInfo(position);
            playerInfo.playerView = playView;
            playerInfo.txVodPlayer.setPlayerView(playView);

            if (playerInfo.reviewstatus.equals(TCVideoInfo.REVIEW_STATUS_NORMAL)) {
                playerInfo.txVodPlayer.startPlay(playerInfo.playURL);
            } else if (playerInfo.reviewstatus.equals(TCVideoInfo.REVIEW_STATUS_NOT_REVIEW)) { // 审核中
            } else if (playerInfo.reviewstatus.equals(TCVideoInfo.REVIEW_STATUS_PORN)) {       // 涉黄

            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            TXCLog.i(TAG, "MyPagerAdapter destroyItem, position = " + position);

            destroyPlayerInfo(position);

            container.removeView((View) object);
        }
    }

    private void initPlayerSDK() {
        mVerticalViewPager.setCurrentItem(mInitTCLiveInfoPosition);
    }

    /**
     * ==========================================来电监听==========================================
     */
    private PhoneStateListener mPhoneListener = null;

    static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<TXVodPlayer> mPlayer;

        public TXPhoneStateListener(TXVodPlayer player) {
            mPlayer = new WeakReference<TXVodPlayer>(player);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TXVodPlayer player = mPlayer.get();
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (player != null) player.setMute(true);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (player != null) player.setMute(true);
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (player != null) player.setMute(false);
                    break;
            }
        }
    }
}
