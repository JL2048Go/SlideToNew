package com.slidetonew;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class NewsAdapter extends ListAdapter<NewsItem, MyViewHolder> {
    private static final int NORMAL_VIEW_TYPE = 0;
    private static final int FOOTER_VIEW_TYPE = 1;
    private static final String TAG = "NewsAdapter";
    int footerViewStatus = NewsViewModel.STATUS_LOAD_MORE;

    private NewsViewModel model;
    NewsAdapter(NewsViewModel viewModel){
        super(DIFF_CALLBACK);
        model = viewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == NORMAL_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cell, parent, false);
            holder = new MyViewHolder(view);
            MyViewHolder finalHolder = holder;
            holder.itemView.setOnClickListener(v -> {
                //把新闻url存到bundle，用新fragment读取打开
                Bundle bundle = new Bundle();
                bundle.putString("contentUrl", getItem(finalHolder.getLayoutPosition()).getUrl());
                Navigation.findNavController(v).navigate(R.id.action_newsFragment_to_newsContentFragment, bundle);

            });
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footerlayout, parent, false);
            holder = new MyViewHolder(view);
            MyViewHolder finalHolder1 = holder;
            view.setOnClickListener(v -> {
                finalHolder1.progressBar.setVisibility(View.VISIBLE);
                finalHolder1.statusText.setText("正在加载");
                model.fetData();
            });
        }
        return holder;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        //根据位置返回视图类型
        return position == (getItemCount()-1)?  FOOTER_VIEW_TYPE: NORMAL_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        //最后一个为尾视图
        if (position == getItemCount() -1){
            switch (footerViewStatus){
                case NewsViewModel.STATUS_LOAD_MORE:
                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.statusText.setText("正在加载");
                    holder.itemView.setClickable(false);
                    break;
                case NewsViewModel.STATUS_NO_MORE:
                    holder.progressBar.setVisibility(View.GONE);
                    holder.statusText.setText("没有更多新闻了");
                    holder.itemView.setClickable(false);
                    break;
                default:
                    holder.progressBar.setVisibility(View.GONE);
                    holder.statusText.setText("网络错误，点击重试");
                    holder.itemView.setClickable(true);
                    break;
            }
            return;
        }
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
            params.setMargins(0,0,0,0);
            holder.itemView.setLayoutParams(params);
            holder.itemView.setVisibility(View.GONE);
        }


    }

    //比较是否相同
    private static final DiffUtil.ItemCallback<NewsItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewsItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
            Log.d(TAG, "areContentsTheSame: " + oldItem.toString() + " " + newItem.toString());
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
            Log.d(TAG, "areContentsTheSame: " + oldItem.getDocid() + " " + newItem.getDocid());
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
    ProgressBar progressBar;
    TextView statusText;

    MyViewHolder(@NonNull View itemView) {
        super(itemView);
        shimmerLayout = itemView.findViewById(R.id.shimmerLayoutNews);
        imageView = itemView.findViewById(R.id.imageView);
        newsTitle = itemView.findViewById(R.id.newsTitle);
        publish = itemView.findViewById(R.id.publish);
        postTime = itemView.findViewById(R.id.postTime);
        progressBar =itemView.findViewById(R.id.progressBar);
        statusText = itemView.findViewById(R.id.statusText);
    }
}