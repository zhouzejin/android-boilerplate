package uk.co.ribot.androidboilerplate.runner

import android.annotation.SuppressLint
import android.app.Application
import android.app.KeyguardManager
import android.os.PowerManager
import android.support.test.runner.AndroidJUnitRunner

import android.content.Context.KEYGUARD_SERVICE
import android.content.Context.POWER_SERVICE
import android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP
import android.os.PowerManager.FULL_WAKE_LOCK
import android.os.PowerManager.ON_AFTER_RELEASE

/**
 * Extension of AndroidJUnitRunner that adds some functionality to unblock the device screen
 * before starting the tests.
 */
open class UnlockDeviceAndroidJUnitRunner : AndroidJUnitRunner() {

    private lateinit var mWakeLock: PowerManager.WakeLock

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission")
    override fun onStart() {
        val application = targetContext.applicationContext as Application
        val simpleName = UnlockDeviceAndroidJUnitRunner::class.java.simpleName
        // Unlock the device so that the tests can input keystrokes.
        (application.getSystemService(KEYGUARD_SERVICE) as KeyguardManager)
                .newKeyguardLock(simpleName)
                .disableKeyguard()
        // Wake up the screen.
        val powerManager = application.getSystemService(POWER_SERVICE) as PowerManager
        mWakeLock = powerManager.newWakeLock(FULL_WAKE_LOCK or ACQUIRE_CAUSES_WAKEUP or
                ON_AFTER_RELEASE, simpleName)
        mWakeLock.acquire()
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        mWakeLock.release()
    }
}
