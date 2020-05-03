package com.slidetonew;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class NewsAdapter extends ListAdapter<NewsItem, MyViewHolder> {
    private static final int NORMAL_VIEW_TYPE = 0;
    private static final int FOOTER_VIEW_TYPE = 1;
    private static final int HEADER_VIEW_TYPE = 2;
    private static final String TAG = "NewsAdapter";
    int footerViewStatus = NewsViewModel.STATUS_LOAD_MORE;
    boolean diffTitle = true;

    private NewsViewModel model;
    NewsAdapter(NewsViewModel viewModel){
        super(DIFF_CALLBACK);
        model = viewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        MyViewHolder holder;
        //头视图类型事件
        if (viewType == HEADER_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headerlayout, parent, false);
            holder = new MyViewHolder(view);
            holder.toutiao.setOnClickListener(v -> {
                model.title = holder.toutiao.getText().toString();
                diffTitle = true;
                model._type.setValue("BBM54PGAwangning");
                model.resetType();
            });
            holder.yule.setOnClickListener(v -> {
                model.title = holder.yule.getText().toString();
                diffTitle = true;
                model._type.setValue("BA10TA81wangning");
                model.resetType();
            });
            holder.tiyu.setOnClickListener(v -> {
                model.title = holder.tiyu.getText().toString();
                diffTitle = true;
                model._type.setValue("BA8E6OEOwangning");
                model.resetType();
            });
            holder.caijing.setOnClickListener(v -> {
                model.title = holder.caijing.getText().toString();
                diffTitle = true;
                model._type.setValue("BA8EE5GMwangning");
                model.resetType();
            });
            holder.junshi.setOnClickListener(v -> {
                model.title = holder.junshi.getText().toString();
                diffTitle = true;
                model._type.setValue("BAI67OGGwangning");
                model.resetType();
            });
            holder.keji.setOnClickListener(v -> {
                model.title = holder.keji.getText().toString();
                diffTitle = true;
                model._type.setValue("BA8D4A3Rwangning");
                model.resetType();
            });
            holder.shouji.setOnClickListener(v -> {
                model.title = holder.shouji.getText().toString();
                diffTitle = true;
                model._type.setValue("BAI6I0O5wangning");
                model.resetType();
            });
            holder.shuma.setOnClickListener(v -> {
                model.title = holder.shuma.getText().toString();
                diffTitle = true;
                model._type.setValue("BAI6JOD9wangning");
                model.resetType();
            });
            holder.shishang.setOnClickListener(v -> {
                model.title = holder.shishang.getText().toString();
                diffTitle = true;
                model._type.setValue("BA8F6ICNwangning");
                model.resetType();
            });
            holder.youxi.setOnClickListener(v -> {
                model.title = holder.youxi.getText().toString();
                diffTitle = true;
                model._type.setValue("BAI6RHDKwangning");
                model.resetType();
            });
            holder.jiaoyu.setOnClickListener(v -> {
                model.title = holder.jiaoyu.getText().toString();
                diffTitle = true;
                model._type.setValue("BA8FF5PRwangning");
                model.resetType();
            });
            holder.jiankang.setOnClickListener(v -> {
                model.title = holder.jiankang.getText().toString();
                diffTitle = true;
                model._type.setValue("BDC4QSV3wangning");
                model.resetType();
            });
            holder.lvyou.setOnClickListener(v -> {
                model.title = holder.lvyou.getText().toString();
                diffTitle = true;
                model._type.setValue("BEO4GINLwangning");
                model.resetType();
            });
        }else if (viewType == NORMAL_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cell, parent, false);
            holder = new MyViewHolder(view);
            MyViewHolder finalHolder = holder;
            holder.itemView.setOnClickListener(v -> {
                //把新闻url存到bundle，用新fragment读取打开
                Bundle bundle = new Bundle();
                bundle.putString("contentUrl", getItem(finalHolder.getLayoutPosition()).getUrl());
                bundle.putString("contentTitle", getItem(finalHolder.getLayoutPosition()).getTitle());
                bundle.putString("contentSource", getItem(finalHolder.getLayoutPosition()).getSource());
                bundle.putString("contentTime", getItem(finalHolder.getLayoutPosition()).getPtime());
                bundle.putString("imageUrl", getItem(finalHolder.getLayoutPosition()).getImgsrc());
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
        return super.getItemCount() ;
    }

    @Override
    public int getItemViewType(int position) {
        //根据位置返回视图类型
        int pos;
        if (position == 0){
            pos = HEADER_VIEW_TYPE;
        } else if (position == getItemCount() - 1){
            pos = FOOTER_VIEW_TYPE;
        } else {
            pos = NORMAL_VIEW_TYPE;
        }
        return pos;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        //尾视图加载更多
        if (getItemViewType(position) == FOOTER_VIEW_TYPE) {
            switch (footerViewStatus) {
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
            //内容视图
        }else if (getItemViewType(position) == NORMAL_VIEW_TYPE) {
            //某些新闻不带url链接，不显示
            String contentUrl = getItem(position).getUrl();
            if (contentUrl.contains("http")) {
//                holder.shimmerLayout.setShimmerAngle(0);
//                holder.shimmerLayout.setShimmerColor(0x55FFFFFF);
//                holder.shimmerLayout.startShimmerAnimation();
                Glide.with(holder.itemView)
                        .load(getItem(position).getImgsrc())
                        .placeholder(R.drawable.ic_photo_gray_24dp)
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
////                                if (holder.shimmerLayout != null) {
////                                    holder.shimmerLayout.stopShimmerAnimation();
////                                }
//                                return false;
//                            }
//                        })
                        .into(holder.imageView);
                holder.newsTitle.setText(getItem(position).getTitle());
                //去掉某些标题莫名其妙后面带的一个#号
                String str = getItem(position).getSource().substring(getItem(position).getSource().length() - 1);
                if (str.equals("#")) {
                    holder.publish.setText(getItem(position).getSource().substring(0, getItem(position).getSource().length() - 1));
                } else {
                    holder.publish.setText(getItem(position).getSource());
                }

                holder.postTime.setText(getItem(position).getPtime().substring(0,16));
            } else {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                params.height = 0;
                params.setMargins(0, 0, 0, 0);
                holder.itemView.setLayoutParams(params);
                holder.itemView.setVisibility(View.GONE);
            }
        } else {
            return;
        }
    }

    //比较是否相同
    private static final DiffUtil.ItemCallback<NewsItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewsItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
            return oldItem.getDocid() == newItem.getDocid();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull NewsItem oldItem, @NonNull NewsItem newItem) {
            return oldItem == newItem;
        }
    };

}
class MyViewHolder extends RecyclerView.ViewHolder {
//    ShimmerLayout shimmerLayout;
    ImageView imageView;
    TextView newsTitle;
    TextView publish;
    TextView postTime;
    ProgressBar progressBar;
    TextView statusText;
    //头视图
    Button toutiao, yule, tiyu, caijing, junshi, keji, shouji, shuma, shishang, youxi, jiaoyu, jiankang, lvyou;


    MyViewHolder(@NonNull View itemView) {
        super(itemView);
//        shimmerLayout = itemView.findViewById(R.id.shimmerLayout);
        imageView = itemView.findViewById(R.id.imageView);
        newsTitle = itemView.findViewById(R.id.newsTitle);
        publish = itemView.findViewById(R.id.publish);
        postTime = itemView.findViewById(R.id.postTime);
        progressBar =itemView.findViewById(R.id.progressBar);
        statusText = itemView.findViewById(R.id.statusText);

        toutiao = itemView.findViewById(R.id.toutiao);
        yule = itemView.findViewById(R.id.yule);
        tiyu = itemView.findViewById(R.id.tiyu);
        caijing = itemView.findViewById(R.id.caijing);
        junshi = itemView.findViewById(R.id.junshi);
        keji = itemView.findViewById(R.id.keji);
        shouji = itemView.findViewById(R.id.shouji);
        shuma = itemView.findViewById(R.id.shuma);
        shishang = itemView.findViewById(R.id.shishang);
        youxi= itemView.findViewById(R.id.youxi);
        jiaoyu= itemView.findViewById(R.id.jiaoyu);
        jiankang = itemView.findViewById(R.id.jiankang);
        lvyou = itemView.findViewById(R.id.lvyou);

    }
}