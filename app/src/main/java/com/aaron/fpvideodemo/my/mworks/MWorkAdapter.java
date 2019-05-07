package com.aaron.fpvideodemo.my.mworks;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * 我的作品
 * Created by linaidao on 2019/4/29.
 */

public class MWorkAdapter extends RecyclerView.Adapter<MWorkAdapter.MyViewHolder> {

    private Context context;

    private List<VideoCommonEntry> my_ugc_list;

    public MWorkAdapter(Context context, List<VideoCommonEntry> my_ugc_list) {
        this.context = context;

        this.my_ugc_list = my_ugc_list;
    }

    @Override
    public MWorkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MWorkAdapter.MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_item_work, parent, false));
    }

    @Override
    public void onBindViewHolder(MWorkAdapter.MyViewHolder holder, int position) {

        holder.sdvImg.setImageURI(Uri.parse(my_ugc_list.get(position).getFrontCover()));
        holder.tv_play_count.setText(my_ugc_list.get(position).getViewerCount());

    }

    @Override
    public int getItemCount() {
        return my_ugc_list == null ? 0 : my_ugc_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        public SimpleDraweeView sdvImg;
        public TextView tv_play_count;

        MyViewHolder(View view) {
            super(view);

            sdvImg = view.findViewById(R.id.sdvImg);
            tv_play_count = view.findViewById(R.id.tv_play_count);

        }
    }
}