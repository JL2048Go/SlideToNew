package com.slidetonew;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
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
            if (mViewModel.scrollToTop){
                recyclerView.scrollToPosition(0);
                mViewModel.scrollToTop = false;
            }
            Log.d(TAG, "onActivityCreated: 数据似乎改变了呢～");
            adapter.submitList(newsItems);
            swipeRefreshLayout.setRefreshing(false);
        });
        //观察类型变化
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> {
            Log.d(TAG, "onActivityCreated: 类型要改变了呢～"+s);
            new Thread(() -> mViewModel.fetData()).start();
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
                if (dy < 0){
                    return;
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int pos = layoutManager.findLastVisibleItemPosition();
                Log.d(TAG, "onScrolled: " + pos + " " + adapter.getItemCount());
                if (pos == adapter.getItemCount() -1){
                    Log.d(TAG, "onScrolled: 开始拼接～");
                    mViewModel.fetData();
                    Log.d(TAG, "onScrolled: 拼接成功～");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

}
