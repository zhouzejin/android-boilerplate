package com.sunny.commonbusiness.base;

import android.app.Application;

import com.sunny.common.utils.LogUtil;

public class BaseApplication extends Application {

    private static BaseApplication sBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication = this;

        LogUtil.initLog();
    }

    public static BaseApplication getApplication() {
        return sBaseApplication;
    }

}
