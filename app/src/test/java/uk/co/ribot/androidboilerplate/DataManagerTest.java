package uk.co.ribot.androidboilerplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitService;
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitHelper or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock PreferencesHelper mMockPreferencesHelper;
    @Mock DatabaseHelper mMockDatabaseHelper;
    @Mock RetrofitHelper mMockRetrofitHelper;
    @Mock RetrofitService mMockRetrofitService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockPreferencesHelper, mMockDatabaseHelper, mMockRetrofitHelper);
    }

    @Test
    public void syncSubjectsEmitsValues() {
        InTheatersEntity inTheatersEntity = TestDataFactory.INSTANCE.makeInTheatersEntity(10);
        List<Subject> subjects = inTheatersEntity.subjects();
        stubSyncSubjectsHelperCalls(inTheatersEntity, subjects);

        TestObserver<Subject> result = new TestObserver<>();
        mDataManager.syncSubjects(mMockRetrofitService).subscribe(result);
        result.assertNoErrors();
        result.assertValueSequence(subjects);
    }

    @Test
    public void syncSubjectsCallsApiAndDatabase() {
        InTheatersEntity inTheatersEntity = TestDataFactory.INSTANCE.makeInTheatersEntity(10);
        List<Subject> subjects = inTheatersEntity.subjects();
        stubSyncSubjectsHelperCalls(inTheatersEntity, subjects);

        mDataManager.syncSubjects(mMockRetrofitService).subscribe(new TestObserver<Subject>());
        // Verify right calls to helper methods
        verify(mMockRetrofitService).getSubjects();
        verify(mMockDatabaseHelper).setSubjects(subjects);
    }

    @Test
    public void syncSubjectsDoesNotCallDatabaseWhenApiFails() {
        when(mMockRetrofitService.getSubjects())
                .thenReturn(Observable.<InTheatersEntity>error(new RuntimeException()));

        mDataManager.syncSubjects(mMockRetrofitService).subscribe(new TestObserver<Subject>());
        // Verify right calls to helper methods
        verify(mMockRetrofitService).getSubjects();
        verify(mMockDatabaseHelper, never()).setSubjects(ArgumentMatchers.<Subject>anyList());
    }

    private void stubSyncSubjectsHelperCalls(InTheatersEntity inTheatersEntity, List<Subject> subjects) {
        // Stub calls to the subject service and database helper.
        when(mMockRetrofitService.getSubjects())
                .thenReturn(Observable.just(inTheatersEntity));
        when(mMockDatabaseHelper.setSubjects(subjects))
                .thenReturn(Observable.fromIterable(subjects));
    }

}
