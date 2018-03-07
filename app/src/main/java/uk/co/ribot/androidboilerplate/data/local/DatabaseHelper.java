package uk.co.ribot.androidboilerplate.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.annotations.VisibleForTesting;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        this(dbOpenHelper, Schedulers.io());
    }

    @VisibleForTesting
    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDb = sqlBrite.wrapDatabaseHelper(dbOpenHelper, scheduler);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<Subject> setSubjects(final Collection<Subject> newSubjects) {
        return Observable.create(new ObservableOnSubscribe<Subject>() {
            @Override
            public void subscribe(ObservableEmitter<Subject> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Subject.TABLE_NAME, null);
                    for (Subject subject : newSubjects) {
                        long result = mDb.insert(Subject.TABLE_NAME,
                                Subject.FACTORY.marshal(subject).asContentValues(),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) emitter.onNext(subject);
                    }
                    transaction.markSuccessful();
                    emitter.onComplete();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Subject>> getSubjects() {
        return mDb.createQuery(Subject.TABLE_NAME,
                Subject.FACTORY.select_all().statement)
                .mapToList(new Function<Cursor, Subject>() {
                    @Override
                    public Subject apply(Cursor cursor) throws Exception {
                        return Subject.MAPPER.map(cursor);
                    }
                });
    }

}
