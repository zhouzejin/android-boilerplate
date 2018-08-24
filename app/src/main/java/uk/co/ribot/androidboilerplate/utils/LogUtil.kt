package uk.co.ribot.androidboilerplate.utils

import timber.log.Timber
import uk.co.ribot.androidboilerplate.BuildConfig

/*
 * Created by Zhou Zejin on 2016/10/9.
 * A log util wrap from Timber.
 */

fun initLog() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
}

/**
 * Log an verbose message with optional format args.
 */
fun v(message: String, vararg args: Any) {
    Timber.v(message, *args)
}

/**
 * Log an verbose exception and a message with optional format args.
 */
fun v(t: Throwable, message: String, vararg args: Any) {
    Timber.v(t, message, *args)
}

/**
 * Log an debug message with optional format args.
 */
fun d(message: String, vararg args: Any) {
    Timber.d(message, *args)
}

/**
 * Log an debug exception and a message with optional format args.
 */
fun d(t: Throwable, message: String, vararg args: Any) {
    Timber.d(t, message, *args)
}

/**
 * Log an info message with optional format args.
 */
fun i(message: String, vararg args: Any) {
    Timber.i(message, *args)
}

/**
 * Log an info exception and a message with optional format args.
 */
fun i(t: Throwable, message: String, vararg args: Any) {
    Timber.i(t, message, *args)
}

/**
 * Log a warning message with optional format args.
 */
fun w(message: String, vararg args: Any) {
    Timber.w(message, *args)
}

/**
 * Log a warning exception and a message with optional format args.
 */
fun w(t: Throwable, message: String, vararg args: Any) {
    Timber.w(t, message, *args)
}

/**
 * Log an error message with optional format args.
 */
fun e(message: String, vararg args: Any) {
    Timber.e(message, *args)
}

/**
 * Log an error exception and a message with optional format args.
 */
fun e(t: Throwable, message: String, vararg args: Any) {
    Timber.e(t, message, *args)
}
