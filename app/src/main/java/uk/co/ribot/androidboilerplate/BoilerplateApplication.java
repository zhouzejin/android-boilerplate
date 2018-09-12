package uk.co.ribot.androidboilerplate;

import android.app.Application;

import com.sunny.common.utils.LogUtil;

public class BoilerplateApplication extends Application  {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.initLog();
    }

}
