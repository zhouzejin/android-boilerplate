package uk.co.ribot.androidboilerplate.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite3.BriteDatabase;
import com.squareup.sqlbrite3.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDb = sqlBrite.wrapDatabaseHelper(dbOpenHelper.getOpenHelper(), Schedulers.io());
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<Subject> setSubjects(final Collection<Subject> newSubjects) {
        return Observable.create(new ObservableOnSubscribe<Subject>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Subject> e) {
                if (e.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Subject.TABLE_NAME, null);
                    for (Subject subject : newSubjects) {
                        long result = mDb.insert(Subject.TABLE_NAME,
                                SQLiteDatabase.CONFLICT_REPLACE,
                                Subject.FACTORY.marshal(subject).asContentValues());
                        if (result >= 0) e.onNext(subject);
                    }
                    transaction.markSuccessful();
                    e.onComplete();
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
                    public Subject apply(@NonNull Cursor cursor) {
                        return Subject.MAPPER.map(cursor);
                    }
                });
    }

}
