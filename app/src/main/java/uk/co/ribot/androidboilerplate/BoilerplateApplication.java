package uk.co.ribot.androidboilerplate;

import android.content.Context;
import android.content.res.Configuration;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginConfig;
import com.sunny.commonbusiness.base.BaseApplication;

public class BoilerplateApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        RePluginConfig rePluginConfig = new RePluginConfig()
                .setMoveFileWhenInstalling(false)
                .setVerifySign(!BuildConfig.DEBUG);
        RePlugin.App.attachBaseContext(this, rePluginConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RePlugin.App.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        /* Not need to be called if your application's minSdkVersion > = 14 */
        RePlugin.App.onConfigurationChanged(config);
    }

}
