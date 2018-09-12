package com.sunny.main;

import android.app.Application;
import android.content.Context;

import com.sunny.main.injection.component.ApplicationComponent;
import com.sunny.main.injection.component.DaggerApplicationComponent;
import com.sunny.main.injection.module.ApplicationModule;

public class MainApplication extends Application {

    ApplicationComponent mApplicationComponent;

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
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
