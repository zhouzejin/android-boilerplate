package uk.co.ribot.androidboilerplate.data.local

import android.database.sqlite.SQLiteDatabase
import android.support.annotation.VisibleForTesting
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper @VisibleForTesting constructor(dbOpenHelper: DbOpenHelper, scheduler: Scheduler) {

    val briteDb: BriteDatabase

    @Inject
    constructor(dbOpenHelper: DbOpenHelper) : this(dbOpenHelper, Schedulers.io())

    init {
        val sqlBrite = SqlBrite.Builder().build()
        briteDb = sqlBrite.wrapDatabaseHelper(dbOpenHelper, scheduler)
    }

    fun setSubjects(newSubjects: Collection<Subject>): Observable<Subject> {
        return Observable.create(fun(emitter) {
            if (emitter.isDisposed) return
            val transaction = briteDb.newTransaction()
            try {
                briteDb.delete(Subject.TABLE_NAME, null)
                for (subject in newSubjects) {
                    val result = briteDb.insert(Subject.TABLE_NAME,
                            Subject.FACTORY.marshal(subject).asContentValues(),
                            SQLiteDatabase.CONFLICT_REPLACE)
                    if (result >= 0) emitter.onNext(subject)
                }
                transaction.markSuccessful()
                emitter.onComplete()
            } finally {
                transaction.end()
            }
        })
    }

    fun getSubjects(): Observable<List<Subject>> {
        return briteDb.createQuery(Subject.TABLE_NAME, Subject.FACTORY.select_all().statement)
                .mapToList { cursor -> Subject.MAPPER.map(cursor) }
    }

}
