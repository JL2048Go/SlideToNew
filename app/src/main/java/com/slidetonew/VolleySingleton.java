package com.slidetonew;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton INSTANCE = null;
    RequestQueue requestQueue;
    private static Context mContext;
    private VolleySingleton(Context context){
        mContext = context;
        requestQueue = getRequestQueue();
    }


    public static synchronized VolleySingleton getINSTANCE(Context context) {
        if (INSTANCE == null){
            INSTANCE = new VolleySingleton(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mContext);
        }
        return requestQueue;
    }
}
