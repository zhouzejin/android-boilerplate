package uk.co.ribot.androidboilerplate;

import android.content.Context;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import uk.co.ribot.androidboilerplate.injection.component.ApplicationComponent;
import uk.co.ribot.androidboilerplate.injection.component.DaggerApplicationComponent;
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule;
import uk.co.ribot.androidboilerplate.utils.LogUtil;

public class BoilerplateApplication extends TinkerApplication {

    ApplicationComponent mApplicationComponent;

    public BoilerplateApplication() {
        super(
                ShareConstants.TINKER_ENABLE_ALL, // tinkerFlags, tinker支持的类型，dex,library，还是全部都支持！
                "uk.co.ribot.androidboilerplate.utils.tinker.BoilerplateApplicationLike", // ApplicationLike的实现类，只能传递字符串
                "com.tencent.tinker.loader.TinkerLoader", // Tinker的加载器，一般来说用默认的即可
                false // tinkerLoadVerifyFlag, 运行加载时是否校验dex与,ib与res的Md5
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.initLog();
    }

    public static BoilerplateApplication get(Context context) {
        return (BoilerplateApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

}
