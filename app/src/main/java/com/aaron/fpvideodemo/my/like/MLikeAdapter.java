package com.aaron.fpvideodemo.my.like;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.fpvideodemo.R;
import com.aaron.fpvideodemo.base.VideoCommonEntry;
import com.aaron.fpvideodemo.my.mworks.MyVideoEntry;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by linaidao on 2019/4/29.
 */

public class MLikeAdapter extends RecyclerView.Adapter<MLikeAdapter.MyViewHolder> {

    private Context context;
    private List<VideoCommonEntry> worksList;

    public MLikeAdapter(Context context, List<VideoCommonEntry> worksList) {
        this.context = context;
        this.worksList = worksList;
    }

    @Override
    public MLikeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MLikeAdapter.MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_item_work, parent, false));
    }

    @Override
    public void onBindViewHolder(MLikeAdapter.MyViewHolder holder, int position) {

        holder.sdvImg.setImageURI(Uri.parse(worksList.get(position).getFrontCover()));
        holder.tv_play_count.setText(worksList.get(position).getLikeCount());
        holder.iv_play.setImageResource(R.mipmap.ic_my_heart);
    }

    @Override
    public int getItemCount() {
        return worksList == null ? 0 : worksList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView sdvImg;
        TextView tv_play_count;
        ImageView iv_play;

        MyViewHolder(View view) {
            super(view);

            sdvImg = view.findViewById(R.id.sdvImg);
            iv_play = view.findViewById(R.id.iv_play);
            tv_play_count = view.findViewById(R.id.tv_play_count);

        }
    }
}