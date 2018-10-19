package com.sunny.main.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.sunny.common.utils.AndroidComponentUtil;
import com.sunny.common.utils.LogUtil;
import com.sunny.common.utils.NetworkUtil;
import com.sunny.common.utils.RxUtil;
import com.sunny.datalayer.model.bean.Subject;
import com.sunny.main.MainApplication;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SyncService extends Service {

    @Inject
    DataManager mDataManager;
    private Disposable mDisposable;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        LogUtil.i("Starting sync...");

        if (!NetworkUtil.isNetworkConnected(this)) {
            LogUtil.i("Sync canceled, connection not available");
            // RePlugin暂不支持PackageManager#setComponentEnabledSetting方法开启或关闭组件
            // AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        RxUtil.dispose(mDisposable);
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
                        LogUtil.w(e, "Error syncing.");
                        stopSelf(startId);
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i("Synced successfully!");
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
                    && NetworkUtil.isNetworkConnected(context)) {
                LogUtil.i("Connection is now available, triggering sync...");
                // RePlugin暂不支持PackageManager#setComponentEnabledSetting方法开启或关闭组件
                // AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }

}
