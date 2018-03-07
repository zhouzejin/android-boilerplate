package uk.co.ribot.androidboilerplate;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.DbOpenHelper;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory;
import uk.co.ribot.androidboilerplate.utils.DefaultConfig;
import uk.co.ribot.androidboilerplate.utils.RxSchedulersOverrideRule;

import static junit.framework.Assert.assertEquals;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setup() {
        if (mDatabaseHelper == null)
            mDatabaseHelper = new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application),
                    mOverrideSchedulersRule.getScheduler());
    }

    @Test
    public void setSubjects() {
        Subject subject1 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 0);
        Subject subject2 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 1);
        List<Subject> subjects = Arrays.asList(subject1, subject2);

        TestObserver<Subject> result = new TestObserver<>();
        mDatabaseHelper.setSubjects(subjects).subscribe(result);
        result.assertNoErrors();
        result.assertValueSequence(subjects);

        Cursor cursor = mDatabaseHelper.getBriteDb().query(Subject.FACTORY.select_all().statement);
        assertEquals(2, cursor.getCount());
        for (Subject subject : subjects) {
            cursor.moveToNext();
            assertEquals(subject, Subject.MAPPER.map(cursor));
        }
    }

    @Test
    public void getSubjects() {
        Subject subject1 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 0);
        Subject subject2 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 1);
        List<Subject> subjects = Arrays.asList(subject1, subject2);

        mDatabaseHelper.setSubjects(subjects).subscribe();

        TestObserver<List<Subject>> result = new TestObserver<>();
        mDatabaseHelper.getSubjects().subscribe(result);
        result.assertNoErrors();
        result.assertValue(subjects);
    }

}
