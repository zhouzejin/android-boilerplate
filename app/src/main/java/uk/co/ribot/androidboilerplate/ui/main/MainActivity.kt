package uk.co.ribot.androidboilerplate.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import uk.co.ribot.androidboilerplate.R
import uk.co.ribot.androidboilerplate.data.SyncService
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity
import uk.co.ribot.androidboilerplate.utils.addFragmentToActivity

class MainActivity : BaseActivity() {

    companion object {
        private const val EXTRA_TRIGGER_SYNC_FLAG =
                "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG"

        /**
         * Return an Intent to start this Activity.
         * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
         * only be set to false during testing.
         */
        fun getStartIntent(context: Context, triggerDataSyncOnCreate: Boolean): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainFragment: MainFragment? = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as? MainFragment
        mainFragment ?: let {
            // Create the fragment
            mainFragment = MainFragment.newInstance()
            addFragmentToActivity(supportFragmentManager, mainFragment!!, R.id.contentFrame)
        }

        if (intent.getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
