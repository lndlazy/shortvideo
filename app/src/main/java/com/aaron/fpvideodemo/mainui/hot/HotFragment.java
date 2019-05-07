package com.aaron.fpvideodemo.mainui.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.base.IBaseFragment;
import com.aaron.fpvideodemo.base.VideoCommonEntry;
import com.aaron.fpvideodemo.common.utils.TCConstants;
import com.aaron.fpvideodemo.common.widget.OnItemClickListener;
import com.aaron.fpvideodemo.mainui.TransformUtils;
import com.aaron.fpvideodemo.mainui.list.VideoListEntry;
import com.aaron.fpvideodemo.mainui.play.VideoPlayActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 热门
 * Created by linaidao on 2019/5/4.
 */

public class HotFragment extends IBaseFragment implements IHotView {

    private List<VideoCommonEntry> videoList = new ArrayList<>();
    private HotAdapter hotAdapter;

    private IHotPresenter iHotPresenter = new IHotPresenter(this);
    private String keyWord;

    int page;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        initView(view);
        page = 1;
        keyWord = "";
        iHotPresenter.hotList(page, keyWord, true);
        return view;
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager doctorLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(doctorLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        hotAdapter = new HotAdapter(getContext(), videoList);
        recyclerView.setAdapter(hotAdapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                Intent m = new Intent(getContext(), VideoPlayActivity.class);
                m.putExtra(TCConstants.TCLIVE_INFO_POSITION, position);
                m.putExtra(TCConstants.TCLIVE_INFO_LIST, (Serializable) TransformUtils.getTransformInfo(videoList));
                startActivity(m);

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    @Override
    public void updateInfo(boolean refush, VideoListEntry.DataBean videoListInfo) {

        if (videoListInfo.getVideo_list() != null) {

            if (refush)
                videoList.clear();

            videoList.addAll(videoListInfo.getVideo_list());

            if (hotAdapter != null)
                hotAdapter.notifyDataSetChanged();

        }

    }
}
