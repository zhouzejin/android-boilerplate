package uk.co.ribot.androidboilerplate.injection.module

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext
import uk.co.ribot.androidboilerplate.utils.imageloader.GlideImageLoader
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader

/**
 * Provide application-level dependencies.
 */
@Module
class ApplicationModule(protected val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideImageLoader(): ImageLoader {
        return GlideImageLoader()
    }

}
