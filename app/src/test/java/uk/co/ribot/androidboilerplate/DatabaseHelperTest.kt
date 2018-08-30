package uk.co.ribot.androidboilerplate

import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper
import uk.co.ribot.androidboilerplate.data.local.DbOpenHelper
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory
import uk.co.ribot.androidboilerplate.utils.DefaultConfig
import uk.co.ribot.androidboilerplate.utils.RxSchedulersOverrideRule
import java.util.*

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [DefaultConfig.EMULATE_SDK])
class DatabaseHelperTest {

    @Rule
    @JvmField
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    private lateinit var mDatabaseHelper: DatabaseHelper

    @Before
    fun setup() {
        mDatabaseHelper = DatabaseHelper(DbOpenHelper(RuntimeEnvironment.application),
                mOverrideSchedulersRule.getScheduler())
    }

    @Test
    fun setSubjects() {
        val subject1 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 0)
        val subject2 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 1)
        val subjects = Arrays.asList(subject1, subject2)

        val result = TestObserver<Subject>()
        mDatabaseHelper.setSubjects(subjects).subscribe(result)
        result.assertNoErrors()
        result.assertValueSequence(subjects)

        val cursor = mDatabaseHelper.briteDb.query(Subject.FACTORY.selectAll())
        assertEquals(2, cursor.count)
        for (subject in subjects) {
            cursor.moveToNext()
            assertEquals(subject, Subject.MAPPER.map(cursor))
        }
    }

    @Test
    fun getSubjects() {
        val subject1 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 0)
        val subject2 = TestDataFactory.makeSubject(TestDataFactory.randomUuid(), 1)
        val subjects = Arrays.asList(subject1, subject2)

        mDatabaseHelper.setSubjects(subjects).subscribe()

        val result = TestObserver<List<Subject>>()
        mDatabaseHelper.getSubjects().subscribe(result)
        result.assertNoErrors()
        result.assertValue(subjects)
    }

}
