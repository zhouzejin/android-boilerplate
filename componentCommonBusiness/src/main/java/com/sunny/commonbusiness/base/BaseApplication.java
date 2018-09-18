package com.sunny.commonbusiness.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sunny.common.utils.LogUtil;
import com.sunny.commonbusiness.BuildConfig;

public class BaseApplication extends Application {

    private static BaseApplication sBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication = this;

        LogUtil.initLog();
        initARouter();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog(); // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(sBaseApplication); // 尽可能早，推荐在Application中初始化
    }

    public static BaseApplication getApplication() {
        return sBaseApplication;
    }

}
