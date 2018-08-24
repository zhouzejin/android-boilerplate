package uk.co.ribot.androidboilerplate.utils.singleton

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * A simple event bus built with RxJava
 */
@Singleton
class RxBus @Inject constructor() {

    private val mBackpressureStrategy = BackpressureStrategy.BUFFER
    private val mBusSubject: PublishSubject<Any>

    init {
        mBusSubject = PublishSubject.create()
    }

    /**
     * Posts an object (usually an Event) to the bus
     */
    fun post(event: Any) {
        mBusSubject.onNext(event)
    }

    /**
     * Observable that will emmit everything posted to the event bus.
     */
    fun observable(): Flowable<Any> {
        return mBusSubject.toFlowable(mBackpressureStrategy)
    }

    /**
     * Observable that only emits events of a specific class.
     * Use this if you only want to subscribe to one type of events.
     */
    fun <T> filteredObservable(eventClass: Class<T>): Flowable<T> {
        return mBusSubject.ofType(eventClass).toFlowable(mBackpressureStrategy)
    }

}
