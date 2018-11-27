package uk.co.ribot.androidboilerplate.utils.tinker;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import uk.co.ribot.androidboilerplate.utils.tinker.log.MyLogImp;

@SuppressWarnings("unused")
public class BoilerplateApplicationLike extends DefaultApplicationLike {

    private Tinker mTinker;

    public BoilerplateApplicationLike(Application application, int tinkerFlags,
                                      boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                      long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        initTinker(base);
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    private void initTinker(Context base) {
        // must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.initFastCrashProtect();
        // should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);
        // optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());
        // installTinker after load multiDex
        // or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        mTinker = Tinker.with(getApplication());
    }

}
