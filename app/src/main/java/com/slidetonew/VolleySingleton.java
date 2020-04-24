package com.slidetonew;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class VolleySingleton {
    @SuppressLint("StaticFieldLeak")
    private static VolleySingleton INSTANCE = null;
    RequestQueue requestQueue;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private VolleySingleton(Context context){
        mContext = context;
        requestQueue = getRequestQueue();
    }


    static synchronized VolleySingleton getINSTANCE(Context context) {
        if (INSTANCE == null){
            INSTANCE = new VolleySingleton(context);
        }
        return INSTANCE;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mContext);
        }
        return requestQueue;
    }
}
