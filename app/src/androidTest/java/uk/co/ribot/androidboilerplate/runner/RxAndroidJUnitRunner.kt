package uk.co.ribot.androidboilerplate.runner

import android.os.Bundle
import android.support.test.espresso.IdlingRegistry

import io.reactivex.plugins.RxJavaPlugins
import uk.co.ribot.androidboilerplate.utils.RxEspressoScheduleHandler

/**
 * Runner that registers a Espresso Indling resource that handles waiting for
 * RxJava Observables to finish.
 * WARNING - Using this runner will block the tests if the application uses long-lived hot
 * Observables such us event buses, etc.
 */
class RxAndroidJUnitRunner : UnlockDeviceAndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle) {
        super.onCreate(arguments)
        val rxEspressoScheduleHandler = RxEspressoScheduleHandler()
        RxJavaPlugins.setScheduleHandler(rxEspressoScheduleHandler)
        IdlingRegistry.getInstance().register(rxEspressoScheduleHandler.mCountingIdlingResource)
    }
}
