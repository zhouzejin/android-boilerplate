package uk.co.ribot.androidboilerplate.data

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uk.co.ribot.androidboilerplate.BoilerplateApplication
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.utils.*
import javax.inject.Inject

class SyncService : Service() {

    @Inject
    internal lateinit var mDataManager: DataManager
    private var mDisposable: Disposable? = null

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SyncService::class.java)
        }

        fun isRunning(context: Context): Boolean {
            return isServiceRunning(context, SyncService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        BoilerplateApplication.get(this).component.inject(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        i("Starting sync...")

        if (!isNetworkConnected(this)) {
            i("Sync canceled, connection not available")
            toggleComponent(this, SyncOnConnectionAvailable::class.java, true)
            stopSelf(startId)
            return Service.START_NOT_STICKY
        }

        dispose(mDisposable)
        mDataManager.syncSubjects()
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Subject> {
                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                    override fun onNext(subject: Subject) {

                    }

                    override fun onError(e: Throwable) {
                        w(e, "Error syncing.")
                        stopSelf(startId)
                    }

                    override fun onComplete() {
                        i("Synced successfully!")
                        stopSelf(startId)
                    }
                })

        return Service.START_STICKY
    }

    override fun onDestroy() {
        dispose(mDisposable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    class SyncOnConnectionAvailable : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION
                    && isNetworkConnected(context)) {
                i("Connection is now available, triggering sync...")
                toggleComponent(context, this.javaClass, false)
                context.startService(getStartIntent(context))
            }
        }
    }

}
