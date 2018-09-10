package uk.co.ribot.androidboilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import com.sunny.commonbusiness.utils.imageloader.GlideImageLoader;
import com.sunny.commonbusiness.utils.imageloader.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader() {
        return new GlideImageLoader();
    }

}
