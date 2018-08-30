package uk.co.ribot.androidboilerplate.utils

import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.ribot.androidboilerplate.utils.singleton.RxBus

class RxBusTest {

    private lateinit var mEventBus: RxBus

    @Rule
    @JvmField
    // Must be added to every test class that targets app code that uses RxJava
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        mEventBus = RxBus()
    }

    @Test
    fun postedObjectsAreReceived() {
        val testSubscriber = TestSubscriber<Any>()
        mEventBus.observable().subscribe(testSubscriber)

        val event1 = Any()
        val event2 = Any()
        mEventBus.post(event1)
        mEventBus.post(event2)

        testSubscriber.assertValues(event1, event2)
    }

    @Test
    fun filteredObservableOnlyReceivesSomeObjects() {
        val testSubscriber = TestSubscriber<String>()
        mEventBus.filteredObservable(String::class.java).subscribe(testSubscriber)

        val stringEvent = "Event"
        val intEvent = 20
        mEventBus.post(stringEvent)
        mEventBus.post(intEvent)

        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue(stringEvent)
    }
}