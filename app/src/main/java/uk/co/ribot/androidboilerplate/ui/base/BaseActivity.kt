package uk.co.ribot.androidboilerplate.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.util.LongSparseArray
import android.support.v7.app.AppCompatActivity

import java.util.concurrent.atomic.AtomicLong

import uk.co.ribot.androidboilerplate.BoilerplateApplication
import uk.co.ribot.androidboilerplate.injection.component.ActivityComponent
import uk.co.ribot.androidboilerplate.injection.component.ConfigPersistentComponent
import uk.co.ribot.androidboilerplate.injection.component.DaggerConfigPersistentComponent
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule
import uk.co.ribot.androidboilerplate.utils.*

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    companion object {
        private const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        private val NEXT_ID = AtomicLong(0)
        private val sComponentsMap = LongSparseArray<ConfigPersistentComponent>()
    }

    private var mConfigPersistentComponent: ConfigPersistentComponent? = null
    private lateinit var mActivityComponent: ActivityComponent
    private var mActivityId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the ConfigPersistentComponent and reuses cached ConfigPersistentComponent
        // if this is being called after a configuration change.
        mActivityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()
        mConfigPersistentComponent = sComponentsMap.get(mActivityId, null)
        mConfigPersistentComponent ?: let {
            i("Creating new ConfigPersistentComponent id=%d", mActivityId)
            mConfigPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(BoilerplateApplication.get(this).component)
                    .build()
            sComponentsMap.put(mActivityId, mConfigPersistentComponent)
        }
        mActivityComponent = mConfigPersistentComponent!!.activityComponent(ActivityModule(this))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, mActivityId)
    }

    override fun onDestroy() {
        if (!isChangingConfigurations) {
            i("Clearing ConfigPersistentComponent id=%d", mActivityId)
            sComponentsMap.remove(mActivityId)
        }
        super.onDestroy()
    }

    fun configPersistentComponent(): ConfigPersistentComponent {
        return mConfigPersistentComponent!!
    }

    fun activityComponent(): ActivityComponent {
        return mActivityComponent
    }

}
