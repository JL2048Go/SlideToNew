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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

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
        //观察数据更新
        mViewModel.getNewsLiveData().observe(getViewLifecycleOwner(), newsItems -> {
            if (mViewModel.scrollToTop){
                Log.d(TAG, "onActivityCreated: 给我滚回去第一行");
                recyclerView.scrollToPosition(0);
                mViewModel.scrollToTop = false;
            }
            Log.d(TAG, "onActivityCreated: 数据似乎改变了呢～");
            adapter.submitList(newsItems);
            swipeRefreshLayout.setRefreshing(false);
        });
        //观察类型变化
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> {
            Log.d(TAG, "onActivityCreated: 类型要改变了呢～");
            mViewModel.resetQuery();
        });
        //观察底部加载变化
        mViewModel.dataStatusLive.observe(getViewLifecycleOwner(), integer -> {
            adapter.footerViewStatus = integer;
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
            if (integer == NewsViewModel.STATUS_NETWORK_ERROR){
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        recyclerView.setAdapter(adapter);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mViewModel.resetQuery();
            swipeRefreshLayout.setRefreshing(false);
        });
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
                if (pos == adapter.getItemCount() -1 || pos == adapter.getItemCount()-4){
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}
