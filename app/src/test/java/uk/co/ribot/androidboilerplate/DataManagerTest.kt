package uk.co.ribot.androidboilerplate

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper
import uk.co.ribot.androidboilerplate.data.remote.RetrofitService
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitHelper or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Mock
    internal lateinit var mMockPreferencesHelper: PreferencesHelper
    @Mock
    internal lateinit var mMockDatabaseHelper: DatabaseHelper
    @Mock
    internal lateinit var mMockRetrofitHelper: RetrofitHelper
    @Mock
    internal lateinit var mMockRetrofitService: RetrofitService

    private lateinit var mDataManager: DataManager

    @Before
    fun setUp() {
        mDataManager = DataManager(mMockPreferencesHelper, mMockDatabaseHelper, mMockRetrofitHelper)
    }

    @Test
    fun syncSubjectsEmitsValues() {
        val inTheatersEntity = TestDataFactory.makeInTheatersEntity(10)
        val subjects = inTheatersEntity.subjects()
        stubSyncSubjectsHelperCalls(inTheatersEntity, subjects)

        val result = TestObserver<Subject>()
        mDataManager.syncSubjects(mMockRetrofitService).subscribe(result)
        result.assertNoErrors()
        result.assertValueSequence(subjects)
    }

    @Test
    fun syncSubjectsCallsApiAndDatabase() {
        val inTheatersEntity = TestDataFactory.makeInTheatersEntity(10)
        val subjects = inTheatersEntity.subjects()
        stubSyncSubjectsHelperCalls(inTheatersEntity, subjects)

        mDataManager.syncSubjects(mMockRetrofitService).subscribe(TestObserver())
        // Verify right calls to helper methods
        verify<RetrofitService>(mMockRetrofitService).subjects
        verify<DatabaseHelper>(mMockDatabaseHelper).setSubjects(subjects)
    }

    @Test
    fun syncSubjectsDoesNotCallDatabaseWhenApiFails() {
        `when`(mMockRetrofitService.subjects)
                .thenReturn(Observable.error(RuntimeException()))

        mDataManager.syncSubjects(mMockRetrofitService).subscribe(TestObserver())
        // Verify right calls to helper methods
        verify<RetrofitService>(mMockRetrofitService).subjects
        verify<DatabaseHelper>(mMockDatabaseHelper, never()).setSubjects(ArgumentMatchers.anyList())
    }

    private fun stubSyncSubjectsHelperCalls(inTheatersEntity: InTheatersEntity, subjects: List<Subject>) {
        // Stub calls to the subject service and database helper.
        `when`(mMockRetrofitService.subjects)
                .thenReturn(Observable.just(inTheatersEntity))
        `when`(mMockDatabaseHelper.setSubjects(subjects))
                .thenReturn(Observable.fromIterable(subjects))
    }

}
