package uk.co.ribot.androidboilerplate.data

import android.support.annotation.VisibleForTesting
import io.reactivex.Observable
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper
import uk.co.ribot.androidboilerplate.data.remote.RetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(val mPreferencesHelper: PreferencesHelper, private val mDatabaseHelper: DatabaseHelper,
            private val mRetrofitHelper: RetrofitHelper) {

    fun syncSubjects(): Observable<Subject> {
        return mRetrofitHelper.retrofitService.subjects
                .concatMap { inTheatersEntity ->
                    mDatabaseHelper.setSubjects(inTheatersEntity.subjects())
                }
    }

    fun getSubjects(): Observable<List<Subject>> = mDatabaseHelper.getSubjects().distinct()

    @VisibleForTesting
    fun syncSubjects(retrofitService: RetrofitService): Observable<Subject> {
        return retrofitService.subjects
                .concatMap { inTheatersEntity ->
                    mDatabaseHelper.setSubjects(inTheatersEntity.subjects())
                }
    }

}
