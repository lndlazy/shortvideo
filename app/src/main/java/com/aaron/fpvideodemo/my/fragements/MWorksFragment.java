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
import com.aaron.fpvideodemo.mainui.list.TCVideoInfo;
import com.aaron.fpvideodemo.mainui.play.VideoPlayActivity;
import com.aaron.fpvideodemo.my.mworks.IWorkPresenter;
import com.aaron.fpvideodemo.my.mworks.IWorkView;
import com.aaron.fpvideodemo.my.mworks.MWorkAdapter;
import com.aaron.fpvideodemo.my.mworks.MyVideoEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作品fragment
 * Created by linaidao on 2019/4/28.
 */

public class MWorksFragment extends IBaseFragment implements IWorkView {

    private RecyclerView recyclerView;

    private IWorkPresenter iWorkPresenter = new IWorkPresenter(this);
    private List<VideoCommonEntry> my_ugc_list = new ArrayList<>();
    private int page;
    private MWorkAdapter mWorkAdapter;
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

        View view = inflater.inflate(R.layout.fragment_my_works, container, false);

        page = 1;
        recyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView();
        iWorkPresenter.getMyWorks(userId, page, "", true);
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
        mWorkAdapter = new MWorkAdapter(getContext(), my_ugc_list);
        recyclerView.setAdapter(mWorkAdapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                Intent m = new Intent(getContext(), VideoPlayActivity.class);
                m.putExtra(TCConstants.TCLIVE_INFO_POSITION, position);
                m.putExtra(TCConstants.TCLIVE_INFO_LIST, (Serializable) TransformUtils.getTransformInfo(my_ugc_list));
                startActivity(m);

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }


    @Override
    public void updateInfo(MyVideoEntry dataInfo, boolean isRefush) {

        if (dataInfo != null &&
                dataInfo.getData() != null &&
                dataInfo.getData().getMy_ugc_list() != null) {

            if (isRefush)
                this.my_ugc_list.clear();

            this.my_ugc_list.addAll(dataInfo.getData().getMy_ugc_list());

            if (mWorkAdapter != null)
                mWorkAdapter.notifyDataSetChanged();

        }

    }
}
