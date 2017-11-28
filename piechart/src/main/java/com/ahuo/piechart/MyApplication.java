package com.ahuo.piechart;

import android.app.Application;

import com.ahuo.tool.util.MyLog;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-9-28
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        MyLog.init(true, "Practice---");

    }
}
