package uk.co.ribot.androidboilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import com.sunny.commonbusiness.utils.imageloader.ImageLoader;
import com.sunny.commonbusiness.utils.singleton.RxBus;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper;
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule;
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext;

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
