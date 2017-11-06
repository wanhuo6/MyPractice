package com.ahuo.fire.niochat;

import android.app.Application;

import com.ahuo.tool.util.MyLog;
import com.ahuo.tool.util.ToastUtil;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-10-18
 */
public class MyApplication extends Application {

    private static MyApplication mApplication;

    public static Application getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        init();
    }

    private void init() {
        MyLog.init(true, "MyChat---");
        ToastUtil.init(this);
    }

}
