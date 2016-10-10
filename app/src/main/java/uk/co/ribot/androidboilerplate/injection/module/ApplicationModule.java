package uk.co.ribot.androidboilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.androidboilerplate.data.remote.SubjectsService;
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext;
import uk.co.ribot.androidboilerplate.utils.imageloader.GlideImageLoader;
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    private ImageLoader mImageLoader;

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
    SubjectsService provideSubjectsService() {
        return SubjectsService.Creator.newSubjectsService();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader() {
        return new GlideImageLoader();
    }

}
