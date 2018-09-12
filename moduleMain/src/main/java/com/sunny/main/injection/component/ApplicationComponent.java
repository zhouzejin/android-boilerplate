package com.sunny.main.injection.component;

import android.app.Application;
import android.content.Context;

import com.sunny.common.injection.qualifier.ApplicationContext;
import com.sunny.common.utils.imageloader.ImageLoader;
import com.sunny.common.utils.singleton.RxBus;
import com.sunny.datalayer.local.db.DatabaseHelper;
import com.sunny.datalayer.local.preferences.PreferencesHelper;
import com.sunny.datalayer.remote.RetrofitHelper;
import com.sunny.main.data.DataManager;
import com.sunny.main.data.SyncService;
import com.sunny.main.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    RetrofitHelper retrofitHelper();
    DataManager dataManager();
    RxBus eventBus();
    ImageLoader imageLoader();

}
