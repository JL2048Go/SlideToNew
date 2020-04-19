package com.slidetonew;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<List<BBM54PGAwangning>> _newsLiveData = new MediatorLiveData<>();
    LiveData<List<BBM54PGAwangning>> newsLiveData ;
    LiveData<List<BBM54PGAwangning>> getNewsLiveData() {
        return _newsLiveData;
    }
    MutableLiveData<String> type;

    public void setType(MutableLiveData<String> type) {
        this.type = type;
    }

    MutableLiveData<String> getType() {
        if (type == null){
            type = new MutableLiveData<>();
            type.setValue("BBM54PGAwangning");
        }
        return type;
    }
    private static String urlHead = "https://3g.163.com/touch/reconstruct/article/list/";
    private static String signal = "/";
    String end = "0-20.html";

    void fetData() {

        StringRequest stringRequest = new StringRequest(urlHead+getType().getValue()+signal+end,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.substring(9, response.length()-1);
                Log.d("res",res);
                Gson gson = new GsonBuilder().create();
                _newsLiveData.setValue(gson.fromJson(res, NewsBean.class).getBbm54PGAwangnings());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ResponseError", error.toString());
                    }
                });
        VolleySingleton.getINSTANCE(getApplication()).requestQueue.add(stringRequest);
    }

}
