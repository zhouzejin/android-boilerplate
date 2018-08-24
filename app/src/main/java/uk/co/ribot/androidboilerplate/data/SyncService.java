package uk.co.ribot.androidboilerplate.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import uk.co.ribot.androidboilerplate.BoilerplateApplication;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.utils.AndroidComponentUtilKt;
import uk.co.ribot.androidboilerplate.utils.LogUtilKt;
import uk.co.ribot.androidboilerplate.utils.NetworkUtilKt;
import uk.co.ribot.androidboilerplate.utils.RxUtilKt;

public class SyncService extends Service {

    @Inject
    DataManager mDataManager;
    private Disposable mDisposable;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtilKt.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BoilerplateApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        LogUtilKt.i("Starting sync...");

        if (!NetworkUtilKt.isNetworkConnected(this)) {
            LogUtilKt.i("Sync canceled, connection not available");
            AndroidComponentUtilKt.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        RxUtilKt.dispose(mDisposable);
        mDataManager.syncSubjects()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Subject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Subject subject) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtilKt.w(e, "Error syncing.");
                        stopSelf(startId);
                    }

                    @Override
                    public void onComplete() {
                        LogUtilKt.i("Synced successfully!");
                        stopSelf(startId);
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null) mDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtilKt.isNetworkConnected(context)) {
                LogUtilKt.i("Connection is now available, triggering sync...");
                AndroidComponentUtilKt.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }

}
