package com.aaron.fpvideodemo.videoeditor.fillter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.common.TCVideoEditerWrapper;
import com.aaron.fpvideodemo.common.widget.BaseRecyclerAdapter;
import com.aaron.fpvideodemo.videoeditor.BaseEditFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 2017/11/6.
 * 静态滤镜的Fragment
 */
public class TCStaticFilterFragment extends BaseEditFragment implements BaseRecyclerAdapter.OnItemClickListener {
    private static final int[] FILTER_ARR = {
            R.mipmap.filter_biaozhun, R.mipmap.filter_yinghong,
            R.mipmap.filter_yunshang, R.mipmap.filter_chunzhen,
            R.mipmap.filter_bailan, R.mipmap.filter_yuanqi,
            R.mipmap.filter_chaotuo, R.mipmap.filter_xiangfen,

            R.mipmap.filter_langman, R.mipmap.filter_qingxin,
            R.mipmap.filter_weimei, R.mipmap.filter_fennen,
            R.mipmap.filter_huaijiu, R.mipmap.filter_landiao,
            R.mipmap.filter_qingliang, R.mipmap.filter_rixi};

    private List<Integer> mFilterList;
    private List<String> mFilerNameList;
    private RecyclerView mRvFilter;
    private StaticFilterAdapter mAdapter;
    private int mCurrentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_static_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFilterList = new ArrayList<Integer>();
        mFilterList.add(R.mipmap.orginal);
        mFilterList.add(R.mipmap.biaozhun);
        mFilterList.add(R.mipmap.yinghong);
        mFilterList.add(R.mipmap.yunshang);
        mFilterList.add(R.mipmap.chunzhen);
        mFilterList.add(R.mipmap.bailan);
        mFilterList.add(R.mipmap.yuanqi);
        mFilterList.add(R.mipmap.chaotuo);
        mFilterList.add(R.mipmap.xiangfen);
        mFilterList.add(R.mipmap.langman);
        mFilterList.add(R.mipmap.qingxin);
        mFilterList.add(R.mipmap.weimei);
        mFilterList.add(R.mipmap.fennen);
        mFilterList.add(R.mipmap.huaijiu);
        mFilterList.add(R.mipmap.landiao);
        mFilterList.add(R.mipmap.qingliang);
        mFilterList.add(R.mipmap.rixi);


        mFilerNameList = new ArrayList<>();
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_original));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_standard));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_cheery));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_cloud));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_pure));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_orchid));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_vitality));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_super));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_fragrance));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_romantic));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_fresh));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_beautiful));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_pink));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_reminiscence));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_blues));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_cool));
        mFilerNameList.add(getResources().getString(R.string.tc_static_filter_fragment_Japanese));

        mRvFilter = (RecyclerView) view.findViewById(R.id.paster_rv_list);
        mRvFilter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new StaticFilterAdapter(mFilterList, mFilerNameList);
        mAdapter.setOnItemClickListener(this);
        mRvFilter.setAdapter(mAdapter);

        mCurrentPosition = TCStaticFilterViewInfoManager.getInstance().getCurrentPosition();
        mAdapter.setCurrentSelectedPos(mCurrentPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        TCStaticFilterViewInfoManager.getInstance().setCurrentPosition(mCurrentPosition);
    }

    @Override
    public void onItemClick(View view, int position) {
        Bitmap bitmap = null;
        if (position == 0) {
            bitmap = null;  // 没有滤镜
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), FILTER_ARR[position - 1]);
        }
        mAdapter.setCurrentSelectedPos(position);
        // 设置滤镜图片
        TCVideoEditerWrapper.getInstance().getEditer().setFilter(bitmap);

        mCurrentPosition = position;
    }

}
