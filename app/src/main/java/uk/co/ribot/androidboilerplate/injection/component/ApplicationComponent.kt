package uk.co.ribot.androidboilerplate.injection.component

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Component
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.data.SyncService
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader
import uk.co.ribot.androidboilerplate.utils.singleton.RxBus

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(syncService: SyncService)

    @ApplicationContext fun context(): Context
    fun application(): Application
    fun preferencesHelper(): PreferencesHelper
    fun databaseHelper(): DatabaseHelper
    fun retrofitHelper(): RetrofitHelper
    fun dataManager(): DataManager
    fun eventBus(): RxBus
    fun imageLoader(): ImageLoader

}
