package com.slidetonew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class NewsFragment extends Fragment {

    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private Bundle bundle;
    private NewsViewModel mViewModel;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(NewsViewModel.class);
        adapter = new NewsAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        mViewModel.getNewsLiveData().observe(getViewLifecycleOwner(), new Observer<List<BBM54PGAwangning>>() {
            @Override
            public void onChanged(List<BBM54PGAwangning> bbm54PGAwangnings) {
                adapter.submitList(bbm54PGAwangnings);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if (mViewModel.getNewsLiveData().getValue() == null) {
            mViewModel.fetData();
        }
        recyclerView.setAdapter(adapter);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.fetData();
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
