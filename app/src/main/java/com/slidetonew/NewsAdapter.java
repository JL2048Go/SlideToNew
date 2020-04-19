package com.slidetonew;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class NewsAdapter extends ListAdapter<BBM54PGAwangning, MyViewHolder> {
    NewsAdapter(){
        super(DIFF_CALLBACK);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cell, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把新闻url存到bundle，用新fragment读取打开
                Bundle bundle = new Bundle();
                bundle.putString("contentUrl", getItem(holder.getAdapterPosition()).getUrl());
                Navigation.findNavController(v).navigate(R.id.action_newsFragment_to_newsContentFragment, bundle);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        //某些新闻不带url链接，不显示
        String contentUrl = getItem(position).getUrl();
        if (contentUrl.contains("http")){
            holder.shimmerLayout.setShimmerAngle(0);
            holder.shimmerLayout.setShimmerColor(0x55FFFFFF);
            holder.shimmerLayout.startShimmerAnimation();
            Glide.with(holder.itemView)
                    .load(getItem(position).getImgsrc())
                    .placeholder(R.drawable.ic_photo_gray_24dp)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (holder.shimmerLayout != null){
                                holder.shimmerLayout.stopShimmerAnimation();
                            }
                            return false;
                        }
                    })
                    .into(holder.imageView);
            holder.newsTitle.setText(getItem(position).getTitle());
            //某些标题莫名其妙后面带一个#号，去掉
            String str = getItem(position).getSource().substring(getItem(position).getSource().length()-1);
            if (str.equals("#")) {
                holder.publish.setText(getItem(position).getSource().substring(0, getItem(position).getSource().length()-1));
            } else {
                holder.publish.setText(getItem(position).getSource());
            }

            holder.postTime.setText(getItem(position).getPtime().substring(0,9));
        } else {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.height = 0;
            holder.itemView.setLayoutParams(params);
            holder.itemView.setVisibility(View.GONE);
        }

    }

    private static final DiffUtil.ItemCallback<BBM54PGAwangning> DIFF_CALLBACK = new DiffUtil.ItemCallback<BBM54PGAwangning>() {
        @Override
        public boolean areItemsTheSame(@NonNull BBM54PGAwangning oldItem, @NonNull BBM54PGAwangning newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull BBM54PGAwangning oldItem, @NonNull BBM54PGAwangning newItem) {
            return oldItem.getDocid().equals(newItem.getDocid());
        }
    };

}
class MyViewHolder extends RecyclerView.ViewHolder {
    ShimmerLayout shimmerLayout;
    ImageView imageView;
    TextView newsTitle;
    TextView publish;
    TextView postTime;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        shimmerLayout = itemView.findViewById(R.id.shimmerLayoutNews);
        imageView = itemView.findViewById(R.id.imageView);
        newsTitle = itemView.findViewById(R.id.newsTitle);
        publish = itemView.findViewById(R.id.publish);
        postTime = itemView.findViewById(R.id.postTime);
    }
}