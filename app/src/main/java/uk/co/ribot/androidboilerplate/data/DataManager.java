package uk.co.ribot.androidboilerplate.data;

import android.support.annotation.VisibleForTesting;

import com.sunny.datalayer.model.bean.Subject;
import com.sunny.datalayer.model.entity.InTheatersEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitService;

@Singleton
public class DataManager {

    private final PreferencesHelper mPreferencesHelper;
    private final DatabaseHelper mDatabaseHelper;
    private final RetrofitHelper mRetrofitHelper;

    @Inject
    public DataManager(PreferencesHelper preferencesHelper, DatabaseHelper databaseHelper,
                       RetrofitHelper retrofitHelper) {
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mRetrofitHelper = retrofitHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Subject> syncSubjects() {
        return mRetrofitHelper.getRetrofitService().getSubjects()
                .concatMap(new Function<InTheatersEntity, ObservableSource<? extends Subject>>() {
                    @Override
                    public ObservableSource<? extends Subject> apply(InTheatersEntity inTheatersEntity)
                            throws Exception {
                        return mDatabaseHelper.setSubjects(inTheatersEntity.subjects());
                    }
                });
    }

    public Observable<List<Subject>> getSubjects() {
        return mDatabaseHelper.getSubjects().distinct();
    }

    @VisibleForTesting
    public Observable<Subject> syncSubjects(RetrofitService retrofitService) {
        return retrofitService.getSubjects()
                .concatMap(new Function<InTheatersEntity, ObservableSource<? extends Subject>>() {
                    @Override
                    public ObservableSource<? extends Subject> apply(InTheatersEntity inTheatersEntity)
                            throws Exception {
                        return mDatabaseHelper.setSubjects(inTheatersEntity.subjects());
                    }
                });
    }

}
