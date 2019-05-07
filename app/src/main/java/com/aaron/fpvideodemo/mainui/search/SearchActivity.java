package com.aaron.fpvideodemo.mainui.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.base.BaseActivity;
import com.aaron.fpvideodemo.common.utils.IndicatorLineUtil;
import com.aaron.fpvideodemo.common.widget.QuickFragmentPageAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面
 * Created by linaidao on 2019/4/28.
 */

public class SearchActivity extends BaseActivity {

    private TabLayout tblayout;
    private ViewPager vp_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_search);

        initView();
    }

    private void initView() {

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tblayout = findViewById(R.id.tb_top);
        vp_content = findViewById(R.id.vp_content);

        tblayout.setupWithViewPager(vp_content, true);

        //修改指示器长度
        tblayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tblayout, 40, 40);
            }
        });


        List<Fragment> fragmentList = new ArrayList<>();
        UserFragment userFragment = new UserFragment();
//        userFragment.setUserId();


        fragmentList.add(userFragment);
        VideoFragment videoFragment = new VideoFragment();
        fragmentList.add(videoFragment);
        QuickFragmentPageAdapter fragmentPageAdapter = new QuickFragmentPageAdapter(getSupportFragmentManager(), fragmentList, new String[]{"用户", "视频"});
        vp_content.setAdapter(fragmentPageAdapter);


        tblayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Logger.d("选中的position:" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
