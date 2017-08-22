package uk.co.ribot.androidboilerplate.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper;

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
                .concatMap(new Func1<InTheatersEntity, Observable<? extends Subject>>() {
                    @Override
                    public Observable<? extends Subject> call(InTheatersEntity inTheatersEntity) {
                        return mDatabaseHelper.setSubjects(inTheatersEntity.subjects());
                    }
                });
    }

    public Observable<List<Subject>> getSubjects() {
        return mDatabaseHelper.getSubjects().distinct();
    }

}
