package uk.co.ribot.androidboilerplate.data.local

import android.content.Context
import android.content.SharedPreferences
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class PreferencesHelper @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val PREF_FILE_NAME = "android_boilerplate_pref_file"
    }

    private val mPref: SharedPreferences

    init {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        mPref.edit().clear().apply()
    }

}
