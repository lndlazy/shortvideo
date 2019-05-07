//package com.aaron.fpvideodemo.mainui.play;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.v4.view.PagerAdapter;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.aaron.fpvideodemo.R;
//import com.aaron.fpvideodemo.base.Basebean;
//import com.aaron.fpvideodemo.common.Constant;
//import com.aaron.fpvideodemo.common.utils.TCConstants;
//import com.aaron.fpvideodemo.common.utils.TCUtils;
//import com.aaron.fpvideodemo.login.TCUserMgr;
//import com.aaron.fpvideodemo.mainui.list.TCVideoInfo;
//import com.aaron.fpvideodemo.mainui.search.SearchActivity;
//import com.aaron.fpvideodemo.my.MyActivity;
//import com.aaron.fpvideodemo.net.BaseNoTObserver;
//import com.aaron.fpvideodemo.net.RetrofitHttpUtil;
//import com.bumptech.glide.Glide;
//import com.orhanobut.logger.Logger;
//import com.tencent.liteav.basic.log.TXCLog;
//import com.tencent.rtmp.ITXVodPlayListener;
//import com.tencent.rtmp.TXLiveConstants;
//import com.tencent.rtmp.TXLog;
//import com.tencent.rtmp.TXVodPlayConfig;
//import com.tencent.rtmp.TXVodPlayer;
//import com.tencent.rtmp.ui.TXCloudVideoView;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
///**
// * Created by linaidao on 2019/5/5.
// */
//
//public class PlayAdapter extends PagerAdapter implements ITXVodPlayListener {
//
//    private static final String TAG = "PlayAdapter";
//
//    TXVodPlayer vodPlayer;
//
//    private Context context;
//    /**
//     * SDK播放器以及配置
//     */
//    private TXVodPlayer mTXVodPlayer;
//
//    public PlayAdapter(Context context) {
//        this.context = context;
//    }
//
//    ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
//
//    protected PlayerInfo instantiatePlayerInfo(int position) {
//        TXCLog.d(TAG, "instantiatePlayerInfo " + position);
//
//        PlayerInfo playerInfo = new PlayerInfo();
//        vodPlayer = new TXVodPlayer(context);
//        //RENDER_ROTATION_PORTRAIT - 常规的竖屏显示，如果是显示人像，则最适合这种模式了
//        //RENDER_ROTATION_LANDSCAPE - 横屏显示，游戏直播比较适合这种模式
//        vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
//        //RENDER_MODE_FULL_FILL_SCREEN - 将图像等比例铺满整个屏幕，多余部分裁剪掉，此模式下画面不留黑边
//        //RENDER_MODE_ADJUST_RESOLUTION - 将图像等比例缩放，缩放后的宽和高都不会超过显示区域，居中显示，可能会留有黑边
//        vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
//
//        vodPlayer.setVodListener(this);
//        TXVodPlayConfig config = new TXVodPlayConfig();
//        config.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/txcache");
//        config.setMaxCacheItems(5);
//        vodPlayer.setConfig(config);
//        vodPlayer.setAutoPlay(false);
//
////            // 暂停
////            vodPlayer.pause();
////            // 继续
////            vodPlayer.resume();
//
//
//        TCVideoInfo tcLiveInfo = mTCLiveInfoList.get(position);
//        playerInfo.playURL = TextUtils.isEmpty(tcLiveInfo.hlsPlayUrl) ? tcLiveInfo.playurl : tcLiveInfo.hlsPlayUrl;
//        playerInfo.txVodPlayer = vodPlayer;
//        playerInfo.reviewstatus = tcLiveInfo.review_status;
//        playerInfo.pos = position;
//        playerInfoList.add(playerInfo);
//
//        return playerInfo;
//    }
//
//    protected void destroyPlayerInfo(int position) {
//
//        Logger.d("======destroyPlayerInfo=======");
//        while (true) {
//            PlayerInfo playerInfo = findPlayerInfo(position);
//            if (playerInfo == null)
//                break;
//            playerInfo.txVodPlayer.stopPlay(true);
//            playerInfoList.remove(playerInfo);
//
//            TXCLog.d(TAG, "destroyPlayerInfo " + position);
//        }
//    }
//
//    public  PlayerInfo findPlayerInfo(int position) {
//        for (int i = 0; i < playerInfoList.size(); i++) {
//             PlayerInfo playerInfo = playerInfoList.get(i);
//            if (playerInfo.pos == position) {
//                return playerInfo;
//            }
//        }
//        return null;
//    }
//
//    public  PlayerInfo findPlayerInfo(TXVodPlayer player) {
//        for (int i = 0; i < playerInfoList.size(); i++) {
//             PlayerInfo playerInfo = playerInfoList.get(i);
//            if (playerInfo.txVodPlayer == player) {
//                return playerInfo;
//            }
//        }
//        return null;
//    }
//
//    public void onDestroy() {
//        for ( PlayerInfo playerInfo : playerInfoList) {
//            playerInfo.txVodPlayer.stopPlay(true);
//        }
//        playerInfoList.clear();
//    }
//
//    @Override
//    public int getCount() {
//        return mTCLiveInfoList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        TXCLog.i(TAG, "MyPagerAdapter instantiateItem, position = " + position);
//        final TCVideoInfo tcLiveInfo = mTCLiveInfoList.get(position);
//
//        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content, null);
//        view.setId(position);
//        // 封面
//        ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);
//        if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) { //涉黄的图片不显示
//            coverImageView.setImageResource(R.mipmap.bg_ugc);
//        } else {
//            TCUtils.blurBgPic(context, coverImageView, tcLiveInfo.frontcover, R.mipmap.bg_ugc);
//        }
//        // 头像
//        CircleImageView ivAvatar = (CircleImageView) view.findViewById(R.id.player_civ_avatar);
//
//        //搜索按钮
//        ImageView iv_search = view.findViewById(R.id.iv_search);
//        //加号
//        final ImageView iv_plus = view.findViewById(R.id.iv_plus);
//
//        iv_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                iLikePresenter.attentionUser(tcLiveInfo.userid, tcLiveInfo.avatar, tcLiveInfo.nickname);
//                iv_plus.setVisibility(View.INVISIBLE);
//
//            }
//        });
//        //视频介绍
//        TextView player_tv_troduce = view.findViewById(R.id.player_tv_troduce);
//        player_tv_troduce.setText(tcLiveInfo.title);
//        //点赞数
//        final TextView tv_heart_count = view.findViewById(R.id.tv_heart_count);
//        tv_heart_count.setText(String.valueOf(tcLiveInfo.likeCount));
//
//        //点赞
//        final ImageView iv_heart_pic = view.findViewById(R.id.iv_heart_pic);
//        iv_heart_pic.setImageResource(tcLiveInfo.isLike ? R.mipmap.ic_heart_true : R.mipmap.ic_heart_false);
//        iv_heart_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                iLikePresenter.videoLikeOperate(tcLiveInfo.isLike ? 0 : 1, tcLiveInfo.fileid);
//                iv_heart_pic.setImageResource(tcLiveInfo.isLike ? R.mipmap.ic_heart_false : R.mipmap.ic_heart_true);
//                tcLiveInfo.isLike = !tcLiveInfo.isLike;
//                tv_heart_count.setText(tcLiveInfo.isLike ? String.valueOf(++tcLiveInfo.likeCount)
//                        : String.valueOf(--tcLiveInfo.likeCount));
//
//            }
//        });
//
//        //分享
//        ImageView iv_share_pic = view.findViewById(R.id.iv_share_pic);
//        //分享数
//        TextView tv_share_count = view.findViewById(R.id.tv_share_count);
//        //暂停
////            final ImageView iv_pause = view.findViewById(R.id.iv_pause);
////            iv_pause.setVisibility(View.INVISIBLE);
//        //点击头像，进入我的页面
//        ivAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent m = new Intent(context, MyActivity.class);
//                context.startActivity(m);
//            }
//        });
//
//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //搜索
//                Intent m = new Intent(context, SearchActivity.class);
//                context.startActivity(m);
//
//            }
//        });
//
//        //增加浏览次数
//        RetrofitHttpUtil.getInstance().vodView(new BaseNoTObserver() {
//            @Override
//            public void onHandleSuccess(Basebean basebean) {
//
//            }
//
//            @Override
//            public void onHandleError(String message) {
//
//            }
//
//
//        }, ++tcLiveInfo.viewerCount, tcLiveInfo.fileid);
//
//
//        //是否关注
//        RetrofitHttpUtil.getInstance().isLikeUserVod(new BaseNoTObserver<Basebean>() {
//            @Override
//            public void onHandleSuccess(Basebean basebean) {
//
//            }
//
//            @Override
//            public void onHandleError(String message) {
//
//            }
//
//        }, Constant.userId, tcLiveInfo.fileid, tcLiveInfo.userid);
//
//
//        Glide.with(context).load(tcLiveInfo.headpic).error(R.mipmap.face).into(ivAvatar);
//        // 姓名
//        TextView tvName = (TextView) view.findViewById(R.id.player_tv_publisher_name);
//        if (TextUtils.isEmpty(tcLiveInfo.nickname) || "null".equals(tcLiveInfo.nickname)) {
//            tvName.setText(TCUtils.getLimitString(tcLiveInfo.userid, 10));
//        } else {
//            tvName.setText(tcLiveInfo.nickname);
//        }
//        TextView tvVideoReviewStatus = (TextView) view.findViewById(R.id.tx_video_review_status);
//        if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_NOT_REVIEW) {
//            tvVideoReviewStatus.setVisibility(View.VISIBLE);
//            tvVideoReviewStatus.setText(R.string.video_not_review);
//        } else if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) {
//            tvVideoReviewStatus.setVisibility(View.VISIBLE);
//            tvVideoReviewStatus.setText(R.string.video_porn);
//        } else if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_NORMAL) {
//            tvVideoReviewStatus.setVisibility(View.GONE);
//        }
//
//        // 获取此player
//        TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
//        playView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (mTXVodPlayer != null) {
//                    if (mTXVodPlayer.isPlaying()) {
//                        mTXVodPlayer.pause();
//                        iv_pause.setVisibility(View.VISIBLE);
//                    } else {
//                        mTXVodPlayer.resume();
//                        iv_pause.setVisibility(View.INVISIBLE);
//                    }
//                }
//
//            }
//        });
//
//        PlayerInfo playerInfo = instantiatePlayerInfo(position);
//        playerInfo.playerView = playView;
//        playerInfo.txVodPlayer.setPlayerView(playView);
//        if (playerInfo.reviewstatus == TCVideoInfo.REVIEW_STATUS_NORMAL) {
//            playerInfo.txVodPlayer.startPlay(playerInfo.playURL);
//        } else if (playerInfo.reviewstatus == TCVideoInfo.REVIEW_STATUS_NOT_REVIEW) { // 审核中
//        } else if (playerInfo.reviewstatus == TCVideoInfo.REVIEW_STATUS_PORN) {       // 涉黄
//
//        }
//
//        container.addView(view);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        TXCLog.i(TAG, "MyPagerAdapter destroyItem, position = " + position);
//
//        destroyPlayerInfo(position);
//
//        container.removeView((View) object);
//    }
//
//    @Override
//    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
//
////        Logger.d("===onPlayEvent===::" + event);
//
//        if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
//            int width = param.getInt(TXLiveConstants.EVT_PARAM1);
//            int height = param.getInt(TXLiveConstants.EVT_PARAM2);
//            if (width > height) {
//                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
//            } else {
//                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
//            }
//        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
//            restartPlay();
//        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {// 视频I帧到达，开始播放
//
//            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
//            if (playerInfo != null) {
//                playerInfo.isBegin = true;
//            }
//            if (mTXVodPlayer == player) {
//                TXLog.i(TAG, "onPlayEvent, event I FRAME, player = " + player);
//                mIvCover.setVisibility(View.GONE);
//                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY, TCUserMgr.getInstance().getUserId(), event, "点播播放成功", new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                    }
//                });
//            }
//        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
//            if (mTXVodPlayer == player) {
//                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);
//                mTXVodPlayer.resume();
//            }
//        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
//            iv_pause.setVisibility(View.INVISIBLE);
//
//            //如下这段代码是处理播放显示的事件，言下之意：不要转菊花了
//            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
//            if (playerInfo != null && playerInfo.isBegin) {
//                mIvCover.setVisibility(View.GONE);
//                TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
//            }
//        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
//
//            iv_pause.setVisibility(View.INVISIBLE);
//
//        } else if (event < 0) {
//            if (mTXVodPlayer == player) {
//                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);
//
//                String desc = null;
//                switch (event) {
//                    case TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL:
//                        desc = "获取加速拉流地址失败";
//                        break;
//                    case TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND:
//                        desc = "文件不存在";
//                        break;
//                    case TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL:
//                        desc = "h265解码失败";
//                        break;
//                    case TXLiveConstants.PLAY_ERR_HLS_KEY:
//                        desc = "HLS解密key获取失败";
//                        break;
//                    case TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL:
//                        desc = "获取点播文件信息失败";
//                        break;
//                }
//                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY, TCUserMgr.getInstance().getUserId(), event, desc, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.i(TAG, "onFailure");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.i(TAG, "onResponse");
//                    }
//                });
//            }
//            Toast.makeText(getActivity(), "event:" + event, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {
//
//    }
//
//
//    class PlayerInfo {
//        public TXVodPlayer txVodPlayer;
//        public String playURL;
//        public boolean isBegin;
//        public View playerView;
//        public int pos;
//        public int reviewstatus;
//    }
//}
