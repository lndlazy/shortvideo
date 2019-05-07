package com.aaron.fpvideodemo.mainui.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.mainui.search.bean.UserEntry;

import java.util.List;

/**
 * Created by linaidao on 2019/4/28.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewholder> {


    private List<UserEntry> userEntryList;
    private Context context;

    public UserAdapter(List<UserEntry> userEntryList,Context context) {
        this.userEntryList = userEntryList;
        this.context = context;

    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewholder(LayoutInflater.from(context).inflate(R.layout.recycler_item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userEntryList == null ?0 : userEntryList.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder {


        public MyViewholder(View itemView) {
            super(itemView);
        }
    }
}
