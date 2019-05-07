package com.aaron.fpvideodemo.my.fragements;

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
import com.aaron.fpvideodemo.mainui.play.VideoPlayActivity;
import com.aaron.fpvideodemo.my.like.ILikeEntry;
import com.aaron.fpvideodemo.my.like.ILikePresenter;
import com.aaron.fpvideodemo.my.like.ILikeView;
import com.aaron.fpvideodemo.my.like.MLikeAdapter;
import com.aaron.fpvideodemo.my.mworks.MyVideoEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的喜欢fragment
 * Created by linaidao on 2019/4/28.
 */

public class MLikeFragment extends IBaseFragment implements ILikeView {

    private RecyclerView recyclerView;
    private int page = 1;
    private ILikePresenter iLikePresenter = new ILikePresenter(this);
    private List<VideoCommonEntry> worksList = new ArrayList<>();
    private MLikeAdapter mWorkAdapter;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_lisks, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView();
        page = 1;
        iLikePresenter.iLikeList(userId, page, "", true);
        return view;
    }

    private void initRecyclerView() {

        GridLayoutManager doctorLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(doctorLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        //添加自定义分割线
//        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_recyclerview_item_gray));
//        recyclerView.addItemDecoration(divider);

//        List<MWorksEntry> worksList = new ArrayList<>();
//
//        worksList.add(new MWorksEntry("10", "http://1252027113.vod2.myqcloud.com/1d71b962vodtranscq1252027113/f8ad90725285890788162283191/1555664062_1582282122.100_0.jpg"));
//        worksList.add(new MWorksEntry("15", "http://1252027113.vod2.myqcloud.com/46f5673avodtransbj1252027113/06656f4f5285890788301074618/1555996529_1727920543.100_0.jpg"));

        mWorkAdapter = new MLikeAdapter(getContext(), worksList);
        recyclerView.setAdapter(mWorkAdapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                Intent m = new Intent(getContext(), VideoPlayActivity.class);
                m.putExtra(TCConstants.TCLIVE_INFO_POSITION, position);
                m.putExtra(TCConstants.TCLIVE_INFO_LIST, (Serializable) TransformUtils.getTransformInfo(worksList));
                startActivity(m);

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    public void updateLikeList(ILikeEntry.DataBean iLikeDataBean, boolean isRefush) {

        if (iLikeDataBean.getMy_like_vod_list() != null) {

            if (isRefush)
                worksList.clear();

            worksList.addAll(iLikeDataBean.getMy_like_vod_list());
            if (mWorkAdapter != null)
                mWorkAdapter.notifyDataSetChanged();

        }

    }
}
