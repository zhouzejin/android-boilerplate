package uk.co.ribot.androidboilerplate.test.common.injection.module

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext
import uk.co.ribot.androidboilerplate.utils.imageloader.GlideImageLoader
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader

import org.mockito.Mockito.mock

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
class ApplicationTestModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    /************* MOCKS *************/

    @Provides
    @Singleton
    internal fun provideDataManager(): DataManager {
        return mock(DataManager::class.java)
    }

    @Provides
    @Singleton
    internal fun provideImageLoader(): ImageLoader {
        return mock(GlideImageLoader::class.java)
    }

}
