package com.aaron.fpvideodemo.mainui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.base.Basebean;
import com.aaron.fpvideodemo.base.IBaseFragment;
import com.aaron.fpvideodemo.common.VideoConstant;
import com.aaron.fpvideodemo.mainui.search.adapter.UserAdapter;
import com.aaron.fpvideodemo.mainui.search.bean.UserEntry;
import com.aaron.fpvideodemo.my.like.ILikeEntry;
import com.aaron.fpvideodemo.my.like.ILikePresenter;
import com.aaron.fpvideodemo.my.like.ILikeView;
import com.aaron.fpvideodemo.net.BaseNoTObserver;
import com.aaron.fpvideodemo.net.RetrofitHttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户fragment
 * Created by linaidao on 2019/4/28.
 */

public class UserFragment extends IBaseFragment implements ILikeView{

    private RecyclerView recyclerView;
    private String userId;
    int page;
    String keyWord;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    private  ILikePresenter iLikePresenter = new ILikePresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        iLikePresenter.fansAndLikeNum(userId);
        initView(view);
        page = 1;
        return view;

    }

    private void initView(View view) {

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        //添加自定义分割线
//        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_home_divider));
//        recyclerView.addItemDecoration(divider);

        myFollowsList();

        List<UserEntry> userList = new ArrayList<>();
        UserEntry u = new UserEntry();
        userList.add(u);
        UserAdapter userAdapter = new UserAdapter(userList, getActivity());
        recyclerView.setAdapter(userAdapter);

    }

    //我关注的用户列表
    private void myFollowsList() {

        RetrofitHttpUtil.getInstance().myLikeUserList(new BaseNoTObserver<Basebean>() {
            @Override
            public void onHandleSuccess(Basebean basebean) {

            }

            @Override
            public void onHandleError(String message) {

            }

        }, VideoConstant.userId, page, VideoConstant.PIGE_SIZE, keyWord);

    }


    @Override
    public void updateLikeList(ILikeEntry.DataBean data, boolean isRefush) {

    }
}
