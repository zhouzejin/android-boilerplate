package uk.co.ribot.androidboilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitService;
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext;
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule;
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader;
import uk.co.ribot.androidboilerplate.utils.singleton.RxBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    RetrofitService retrofitService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxBus eventBus();
    ImageLoader imageLoader();

}
