package com.sunny.main;

import com.sunny.commonbusiness.BaseApplication;
import com.sunny.main.injection.component.ApplicationComponent;
import com.sunny.main.injection.component.DaggerApplicationComponent;
import com.sunny.main.injection.module.ApplicationModule;

public class MainApplication extends BaseApplication {

    static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static ApplicationComponent getComponent() {
        if (sApplicationComponent == null) {
            sApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(getApplication()))
                    .build();
        }
        return sApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public static void setComponent(ApplicationComponent component) {
        sApplicationComponent = component;
    }

}
