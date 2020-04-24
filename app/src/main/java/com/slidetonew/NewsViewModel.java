package com.slidetonew;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NewsViewModel extends AndroidViewModel {
    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    private static final String TAG = "NewsViewModel";
    static final int STATUS_LOAD_MORE = 0;
    static final int STATUS_NO_MORE = 1;
    static final int STATUS_NETWORK_ERROR= 2;

    private MutableLiveData<List<NewsItem>> _newsLiveData = new MutableLiveData<>();
    LiveData<List<NewsItem>> getNewsLiveData() {
        return _newsLiveData;
    }
    MutableLiveData<Integer> dataStatusLive = new MutableLiveData<>();

    public LiveData<Integer> getDataStatusLive() {
        return dataStatusLive;
    }

    /*
                0: 起始页
                10: 加载数量10(20)
                https://3g.163.com/touch/reconstruct/article/list/BBM54PGAwangning/0-10.html
                 */
    private static String urlHead = "https://3g.163.com/touch/reconstruct/article/list/";
    private static String slash = "/";
    private int currentStart = 0;
    private static String end = "-20.html";
    MutableLiveData<String> type;
    LiveData<String> getType() {
        if (type == null){
            type = new MutableLiveData<>();
            type.setValue("BBM54PGAwangning");
        }
        return type;
    }

    boolean scrollToTop = true;
    private boolean isNewQuery = true;
    private boolean isLoading = false;
    void resetQuery(){
        scrollToTop = true;
        fetData();
    }

    void fetData(){
        if (isLoading) return;
        isLoading = true;
        //加载数量最大300
        if (currentStart > 300) {
            dataStatusLive.setValue(STATUS_NO_MORE);
            return;
        }
        String url = urlHead+getType().getValue()+slash+currentStart+end;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    String res = response.substring(9, response.length()-1);
                    Gson gson = new GsonBuilder().create();
                    switch (Objects.requireNonNull(getType().getValue())){
                        case "BBM54PGAwangning" :
                            Log.d(TAG, "fetData: 点击头条啦！");
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBBM54PGAwangning());
                            } else {
                                //拼接数据
                                Log.d(TAG, "fetData: 数据有拼接了呢～");
                                boolean add = _newsLiveData.getValue().addAll(gson.fromJson(res, NewsBean.class).getBBM54PGAwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA10TA81wangning" :
                            Log.d(TAG, "fetData: 点击娱乐啦！");
                            if (isNewQuery){
                                Log.d(TAG, "fetData: 娱乐执行了呀～");
                                Log.d(TAG, "fetData: before " + _newsLiveData.getValue());
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA10TA81wangning());
                                Log.d(TAG, "fetData: after " + _newsLiveData.getValue());
                            } else {
                                //拼接数据
                                Log.d(TAG, "fetData: 数据有拼接了呢～");
                                boolean add = _newsLiveData.getValue().addAll(gson.fromJson(res, NewsBean.class).getBA10TA81wangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA8E6OEOwangning" :
                            Log.d(TAG, "fetData: 点击体育啦！");
                            if (isNewQuery){
                                Log.d(TAG, "fetData: 体育执行了呀～");
                                Log.d(TAG, "fetData: before " + _newsLiveData.getValue());
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA8E6OEOwangning());
                                Log.d(TAG, "fetData: after " + _newsLiveData.getValue());
                            } else {
                                //拼接数据
                                Log.d(TAG, "fetData: 数据有拼接了呢～");
                                boolean add = _newsLiveData.getValue().addAll(gson.fromJson(res, NewsBean.class).getBA8E6OEOwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;

                    }
                    dataStatusLive.setValue(STATUS_LOAD_MORE);
                    isLoading = false;
                    isNewQuery = false;
                    currentStart += 20;
                },
                error -> {
                    dataStatusLive.setValue(STATUS_NETWORK_ERROR);
                    isLoading = false;
                }
        );
        VolleySingleton.getINSTANCE(getApplication()).requestQueue.add(stringRequest);
    }



}
