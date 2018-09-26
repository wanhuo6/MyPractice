package com.ahuo.practise;

import android.app.Application;

import com.ahuo.tool.util.MyLog;
import com.ahuo.tool.util.ToastUtil;

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
        ToastUtil.init(this);

    }
}
