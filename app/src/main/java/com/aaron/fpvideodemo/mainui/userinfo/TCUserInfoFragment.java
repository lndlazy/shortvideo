package com.aaron.fpvideodemo.mainui.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.base.IBaseFragment;
import com.aaron.fpvideodemo.common.VideoConstant;
import com.aaron.fpvideodemo.common.utils.IndicatorLineUtil;
import com.aaron.fpvideodemo.common.widget.QuickFragmentPageAdapter;
import com.aaron.fpvideodemo.my.fragements.MActiveFragment;
import com.aaron.fpvideodemo.my.fragements.MLikeFragment;
import com.aaron.fpvideodemo.my.fragements.MWorksFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户资料展示页面
 */
public class TCUserInfoFragment extends IBaseFragment {

    private android.widget.ImageView ivheapbg;
    private android.widget.ImageView ivback;
    private de.hdodenhof.circleimageview.CircleImageView civheadpic;
    private android.widget.TextView tvmyname;
    private android.widget.TextView tvmyid;
    private android.support.design.widget.TabLayout tableLayout;
    private android.support.v4.view.ViewPager vpcontent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.act_my, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {


        vpcontent = (ViewPager) view.findViewById(R.id.vp_content);
        tableLayout = (TabLayout) view.findViewById(R.id.tb_content);
        tvmyid = (TextView) view.findViewById(R.id.tv_my_id);
        tvmyname = (TextView) view.findViewById(R.id.tv_my_name);
        civheadpic = (CircleImageView) view.findViewById(R.id.civ_head_pic);
        ivback = (ImageView) view.findViewById(R.id.iv_back);
        ivheapbg = (ImageView) view.findViewById(R.id.iv_heap_bg);

        tableLayout.setupWithViewPager(vpcontent, true);
        //修改指示器长度
        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(tableLayout, 30, 30);
            }
        });

        tvmyname.setText(VideoConstant.userName);
        tvmyid.setText("ID号：" + VideoConstant.userId);
//        civheadpic.setImageURI(Uri.parse(avatar));
        Glide.with(this).load(VideoConstant.userImg).error(R.mipmap.face).into(civheadpic);


        List<Fragment> fragmentList = new ArrayList();

        MWorksFragment mWorksFragment = new MWorksFragment();
        mWorksFragment.setUserId(VideoConstant.userId);
        fragmentList.add(mWorksFragment);

        MActiveFragment mActiveFragment = new MActiveFragment();
        mActiveFragment.setUserId(VideoConstant.userId);
        fragmentList.add(mActiveFragment);

        MLikeFragment mLikeFragment = new MLikeFragment();
        mLikeFragment.setUserId(VideoConstant.userId);
        fragmentList.add(mLikeFragment);

        QuickFragmentPageAdapter fragmentPageAdapter = new QuickFragmentPageAdapter(getActivity().getSupportFragmentManager(),
                fragmentList, new String[]{"作品", "动态", "喜欢"});

        vpcontent.setAdapter(fragmentPageAdapter);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
