package uk.co.ribot.androidboilerplate.utils

import android.support.test.espresso.idling.CountingIdlingResource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * Espresso Idling resource that handles waiting for RxJava Observables executions.
 * This class must be used with RxIdlingExecutionHook.
 * Before registering this idling resource you must:
 * 1. Create an instance of this class.
 * 2. Register RxEspressoScheduleHandler with the RxJavaPlugins using setScheduleHandler()
 * 3. Register this idle resource with Espresso using Espresso.registerIdlingResources()
 */
class RxEspressoScheduleHandler : Function<Runnable, Runnable> {

    val mCountingIdlingResource = CountingIdlingResource("rxJava")

    override fun apply(@NonNull runnable: Runnable): Runnable {
        return Runnable {
            mCountingIdlingResource.increment()

            try {
                runnable.run()
            } finally {
                mCountingIdlingResource.decrement()
            }
        }
    }

}
