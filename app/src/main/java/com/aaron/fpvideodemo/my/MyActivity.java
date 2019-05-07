package com.aaron.fpvideodemo.my;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.base.BaseActivity;
import com.aaron.fpvideodemo.common.Constant;
import com.aaron.fpvideodemo.common.utils.IndicatorLineUtil;
import com.aaron.fpvideodemo.common.widget.QuickFragmentPageAdapter;
import com.aaron.fpvideodemo.mainui.play.TCVodPlayerActivity;
import com.aaron.fpvideodemo.my.fragements.MActiveFragment;
import com.aaron.fpvideodemo.my.fragements.MLikeFragment;
import com.aaron.fpvideodemo.my.fragements.MWorksFragment;
import com.aaron.fpvideodemo.my.like.ILikeEntry;
import com.aaron.fpvideodemo.my.like.ILikePresenter;
import com.aaron.fpvideodemo.my.like.ILikeView;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 * Created by linaidao on 2019/4/28.
 */

public class MyActivity extends BaseActivity implements ILikeView {

    private android.widget.ImageView ivheapbg;
    private android.widget.ImageView ivback;
    private de.hdodenhof.circleimageview.CircleImageView civheadpic;
    //    private SimpleDraweeView civheadpic;
    private android.widget.TextView tvmyname;
    private android.widget.TextView tvmyid;
    private android.support.design.widget.TabLayout tableLayout;
    private android.support.v4.view.ViewPager vpcontent;
    private String nickName;
    private String userId;
    private String avatar;

    private ILikePresenter iLikePresenter = new ILikePresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_my);

        nickName = getIntent().getStringExtra("nickName");
        userId = getIntent().getStringExtra("userId");
        avatar = getIntent().getStringExtra("avatar");

        iLikePresenter.fansAndLikeNum(userId);
        initView();
    }

    private void initView() {
        vpcontent = (ViewPager) findViewById(R.id.vp_content);
        tableLayout = (TabLayout) findViewById(R.id.tb_content);
        tvmyid = (TextView) findViewById(R.id.tv_my_id);
        tvmyname = (TextView) findViewById(R.id.tv_my_name);
        civheadpic = findViewById(R.id.civ_head_pic);
        ivback = (ImageView) findViewById(R.id.iv_back);
        ivheapbg = (ImageView) findViewById(R.id.iv_heap_bg);

        tableLayout.setupWithViewPager(vpcontent, true);
        //修改指示器长度
        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tableLayout, 30, 30);
            }
        });

        tvmyname.setText(nickName);
        tvmyid.setText("ID号：" + userId);
//        civheadpic.setImageURI(Uri.parse(avatar));
        Glide.with(this).load(avatar).error(R.mipmap.face).into(civheadpic);

        List<Fragment> fragmentList = new ArrayList();

        MWorksFragment mWorksFragment = new MWorksFragment();
        mWorksFragment.setUserId(userId);
        fragmentList.add(mWorksFragment);

//        MActiveFragment mActiveFragment = new MActiveFragment();
//        mActiveFragment.setUserId(userId);
//        fragmentList.add(mActiveFragment);

        MLikeFragment mLikeFragment = new MLikeFragment();
        mLikeFragment.setUserId(userId);
        fragmentList.add(mLikeFragment);

        QuickFragmentPageAdapter fragmentPageAdapter = new QuickFragmentPageAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"作品", "喜欢"});

//        QuickFragmentPageAdapter fragmentPageAdapter = new QuickFragmentPageAdapter(getSupportFragmentManager(),
//                fragmentList, new String[]{"作品", "动态", "喜欢"});

        vpcontent.setAdapter(fragmentPageAdapter);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void updateLikeList(ILikeEntry.DataBean data, boolean isRefush) {

    }
}
