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
    private static final String TAG = "NewsViewModel";

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    static final int STATUS_LOAD_MORE = 0;
    static final int STATUS_NO_MORE = 1;
    static final int STATUS_NETWORK_ERROR= 2;

    public String title = "头条";

    private MutableLiveData<List<NewsItem>> _newsLiveData = new MutableLiveData<>();
    LiveData<List<NewsItem>> newsLiveData;
    LiveData<List<NewsItem>> getNewsLiveData() {
        return _newsLiveData;
    }
    private MutableLiveData<Integer> _dataStatusLive = new MutableLiveData<>();
    LiveData<Integer> dataStatusLive;
    LiveData<Integer> getDataStatusLive() {
        return _dataStatusLive;
    }

    /*
            BBM54PGAwangning： 类型
           0: 起始页
           10: 加载数量10(20)
           https://3g.163.com/touch/reconstruct/article/list/BBM54PGAwangning/0-10.html
        */
    private static String urlHead = "https://3g.163.com/touch/reconstruct/article/list/";
    private static String slash = "/";
    int currentStart = 0;
    private static int maxPage = 300;
    private static String end = "-20.html";
    MutableLiveData<String> _type;
    LiveData<String> type;

    LiveData<String> getType() {
        if (_type == null){
            _type = new MutableLiveData<>();
            _type.setValue("BBM54PGAwangning");
        }
        return _type;
    }

    boolean isNewQuery = true;
    boolean isLoading = true;
    void resetType(){
        currentStart = 0;
        isNewQuery = true;
        fetData();
    }

    void fetData(){
        //加载数量最大300
        if (currentStart > maxPage) {
            _dataStatusLive.setValue(STATUS_NO_MORE);
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
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBBM54PGAwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBBM54PGAwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA10TA81wangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA10TA81wangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBA10TA81wangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA8E6OEOwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA8E6OEOwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBA8E6OEOwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA8EE5GMwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA8EE5GMwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBA8EE5GMwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BAI67OGGwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBAI67OGGwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBAI67OGGwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA8D4A3Rwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA8D4A3Rwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBA8D4A3Rwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BAI6I0O5wangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBAI6I0O5wangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBAI6I0O5wangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BAI6JOD9wangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBAI6JOD9wangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBAI6JOD9wangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA8F6ICNwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA8F6ICNwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBA8F6ICNwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BAI6RHDKwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBAI6RHDKwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBAI6RHDKwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BA8FF5PRwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBA8FF5PRwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBA8FF5PRwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BDC4QSV3wangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBDC4QSV3wangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBDC4QSV3wangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;
                        case "BEO4GINLwangning" :
                            if (isNewQuery){
                                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBEO4GINLwangning());
                            } else {
                                //拼接数据
                                boolean add = Objects.requireNonNull(_newsLiveData.getValue()).addAll(gson.fromJson(res, NewsBean.class).getBEO4GINLwangning());
                                _newsLiveData.setValue(_newsLiveData.getValue());
                            }
                            break;

                    }
                    _dataStatusLive.setValue(STATUS_LOAD_MORE);
                    isLoading = false;
                    isNewQuery = false;
                    currentStart += 20;
                },
                error -> {
                    _dataStatusLive.setValue(STATUS_NETWORK_ERROR);
                }
        );
        VolleySingleton.getINSTANCE(getApplication()).requestQueue.add(stringRequest);
    }



}
