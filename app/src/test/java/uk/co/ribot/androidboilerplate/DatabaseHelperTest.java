package uk.co.ribot.androidboilerplate;

import android.database.Cursor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import rx.observers.TestSubscriber;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.DbOpenHelper;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory;
import uk.co.ribot.androidboilerplate.util.DefaultConfig;
import uk.co.ribot.androidboilerplate.util.RxSchedulersOverrideRule;

import static junit.framework.Assert.assertEquals;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    private final DatabaseHelper mDatabaseHelper =
            new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application));

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Test
    public void setRibots() {
        Subject subject1 = TestDataFactory.makeSubject(TestDataFactory.randomUuid());
        Subject subject2 = TestDataFactory.makeSubject(TestDataFactory.randomUuid());
        List<Subject> subjects = Arrays.asList(subject1, subject2);

        TestSubscriber<Subject> result = new TestSubscriber<>();
        mDatabaseHelper.setSubjects(subjects).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(subjects);

        Cursor cursor = mDatabaseHelper.getBriteDb().query(Subject.SELECT_ALL);
        assertEquals(2, cursor.getCount());
        for (Subject subject : subjects) {
            cursor.moveToNext();
            assertEquals(subject, Subject.MAPPER.map(cursor));
        }
    }

    @Test
    public void getRibots() {
        Subject subject1 = TestDataFactory.makeSubject(TestDataFactory.randomUuid());
        Subject subject2 = TestDataFactory.makeSubject(TestDataFactory.randomUuid());
        List<Subject> subjects = Arrays.asList(subject1, subject2);

        mDatabaseHelper.setSubjects(subjects).subscribe();

        TestSubscriber<List<Subject>> result = new TestSubscriber<>();
        mDatabaseHelper.getSubjects().subscribe(result);
        result.assertNoErrors();
        result.assertValue(subjects);
    }

}
