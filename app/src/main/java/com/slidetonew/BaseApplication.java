package com.slidetonew;

import android.app.Application;
import android.content.Context;


//获取全局context
public class BaseApplication extends Application {
    private static Context mContext;
    public void onCreate(){
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
