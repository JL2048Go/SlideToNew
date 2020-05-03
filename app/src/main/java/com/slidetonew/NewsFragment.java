package com.slidetonew;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsViewModel mViewModel;
    private FloatingActionButton upToTop;
    private TextView titleTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        upToTop = view.findViewById(R.id.upToTop);
        titleTextView = view.findViewById(R.id.titleTextView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(NewsViewModel.class);
        adapter = new NewsAdapter(mViewModel);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //观察数据更新
        mViewModel.getNewsLiveData().observe(getViewLifecycleOwner(), newsItems -> {
            adapter.submitList(newsItems);
            swipeRefreshLayout.setRefreshing(false);
        });
        //观察类型变化
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> {
            Log.d(TAG, "onActivityCreated: 数据类型变化了");
            mViewModel.fetData();
        });
        //观察底部加载变化
        mViewModel.getDataStatusLive().observe(getViewLifecycleOwner(), integer -> {
            adapter.footerViewStatus = integer;
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
            if (integer == NewsViewModel.STATUS_NETWORK_ERROR){
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mViewModel.resetType();
            swipeRefreshLayout.setRefreshing(false);
        });
        //上拉加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter.diffTitle){
                    titleTextView.setText(fetTitle());
                    adapter.diffTitle = false;
                }
                //dy<0为向上滚动，反之向下
                if (dy < 0){
                    upToTop.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onScrolled: 向上滚动了");
                } else {
                    upToTop.setVisibility(View.GONE);
                    Log.d(TAG, "onScrolled: 向下滚动了");
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int pos = layoutManager.findLastVisibleItemPosition();
                Log.d(TAG, "onScrolled: " + pos + " " + adapter.getItemCount());
                if (pos == adapter.getItemCount() -1){
                    mViewModel.fetData();
                } else return;
                Log.d(TAG, "onScrolled: " + adapter.diffTitle);
            }
        });
        //快速回到顶部按钮
        upToTop.setOnClickListener(v -> {
            recyclerView.smoothScrollToPosition(0);
        });
    }

    //获取类型显示在工具栏上
    private String fetTitle() {
        return mViewModel.title;
    }

}
