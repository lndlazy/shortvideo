package com.aaron.fpvideodemo.mainui.hot;

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
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 热门
 * Created by linaidao on 2019/5/4.
 */

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.MyViewHolder> {

    private Context context;
    private List<VideoCommonEntry> videoList;

    public HotAdapter(Context context, List<VideoCommonEntry> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public HotAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotAdapter.MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_item_hot, parent, false));
    }

    @Override
    public void onBindViewHolder(HotAdapter.MyViewHolder holder, int position) {

        holder.sdvBg.setImageURI(Uri.parse(videoList.get(position).getFrontCover()));
        holder.sdvImg.setImageURI(Uri.parse(videoList.get(position).getUserImg()));
        holder.tvTitle.setText(videoList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView sdvBg;
        SimpleDraweeView sdvImg;
        TextView tvTitle;

        MyViewHolder(View view) {
            super(view);

            sdvBg = view.findViewById(R.id.sdvBg);
            sdvImg = view.findViewById(R.id.sdvImg);
            tvTitle = view.findViewById(R.id.tvTitle);

        }
    }
}