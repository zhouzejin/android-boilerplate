package uk.co.ribot.androidboilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import com.sunny.common.injection.qualifier.ApplicationContext;
import com.sunny.common.utils.imageloader.ImageLoader;
import com.sunny.common.utils.singleton.RxBus;
import com.sunny.datalayer.local.db.DatabaseHelper;
import com.sunny.datalayer.local.preferences.PreferencesHelper;
import com.sunny.datalayer.remote.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule;

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
