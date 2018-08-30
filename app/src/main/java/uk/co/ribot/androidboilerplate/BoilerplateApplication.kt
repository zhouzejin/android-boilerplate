package uk.co.ribot.androidboilerplate

import android.app.Application
import android.content.Context

import uk.co.ribot.androidboilerplate.injection.component.ApplicationComponent
import uk.co.ribot.androidboilerplate.injection.component.DaggerApplicationComponent
import uk.co.ribot.androidboilerplate.injection.module.ApplicationModule
import uk.co.ribot.androidboilerplate.utils.*

class BoilerplateApplication : Application() {

    // Needed to replace the component with a test specific one
    var component: ApplicationComponent? = null
        get() {
            if (field == null) {
                field = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
            }
            return field
        }

    override fun onCreate() {
        super.onCreate()

        initLog()
    }

    companion object {
        fun get(context: Context): BoilerplateApplication {
            return context.applicationContext as BoilerplateApplication
        }
    }

}
