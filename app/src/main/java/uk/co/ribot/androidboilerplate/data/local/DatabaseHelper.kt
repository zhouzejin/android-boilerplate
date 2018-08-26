package uk.co.ribot.androidboilerplate.data.local

import android.support.annotation.VisibleForTesting
import com.squareup.sqlbrite3.BriteDatabase
import com.squareup.sqlbrite3.SqlBrite
import com.sunny.sql.SubjectModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DatabaseHelper @VisibleForTesting constructor(dbOpenHelper: DbOpenHelper, scheduler: Scheduler) {

    val briteDb: BriteDatabase

    private val subjectFactory: SubjectModel.Factory<*>
    private val subjectDeleteAll: SubjectModel.DeleteAll
    private val subjectInsertRow: SubjectModel.InsertRow

    @Inject
    constructor(dbOpenHelper: DbOpenHelper) : this(dbOpenHelper, Schedulers.io())

    init {
        val sqlBrite = SqlBrite.Builder().build()
        briteDb = sqlBrite.wrapDatabaseHelper(dbOpenHelper.openHelper, scheduler)

        subjectFactory = Subject.FACTORY
        subjectDeleteAll = SubjectModel.DeleteAll(briteDb.writableDatabase)
        subjectInsertRow = SubjectModel.InsertRow(briteDb.writableDatabase, Subject.FACTORY)
    }

    open fun setSubjects(newSubjects: Collection<Subject>): Observable<Subject> {
        return Observable.create(fun(emitter) {
            if (emitter.isDisposed) return
            val transaction = briteDb.newTransaction()
            try {
                briteDb.executeUpdateDelete(subjectDeleteAll.table, subjectDeleteAll)
                for (subject in newSubjects) {
                    subjectInsertRow.clearBindings()
                    subjectInsertRow.bind(subject.id(), subject.rating(), subject.genres(),
                            subject.title(), subject.casts(), subject.collect_count(),
                            subject.original_title(), subject.subtype(), subject.directors(),
                            subject.year(), subject.images(), subject.alt())
                    val result = briteDb.executeInsert(subjectInsertRow.table, subjectInsertRow)
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
        return briteDb.createQuery(subjectFactory.selectAll().tables,
                subjectFactory.selectAll())
                .mapToList { cursor -> Subject.MAPPER.map(cursor) }
    }

}
