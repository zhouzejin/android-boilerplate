package uk.co.ribot.androidboilerplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.data.model.entity.InTheatersEntity;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper;
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock PreferencesHelper mMockPreferencesHelper;
    @Mock DatabaseHelper mMockDatabaseHelper;
    @Mock RetrofitHelper mMockRetrofitHelper;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(
                mMockPreferencesHelper,
                mMockDatabaseHelper,
                mMockRetrofitHelper);
    }

    @Test
    public void syncSubjectsEmitsValues() {
        List<Subject> subjects = Arrays.asList(
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()),
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()));
        stubSyncSubjectsHelperCalls(subjects);

        TestObserver<Subject> result = new TestObserver<>();
        mDataManager.syncSubjects().subscribe(result);
        result.assertNoErrors();
        result.assertValueSequence(subjects);
    }

    @Test
    public void syncSubjectsCallsApiAndDatabase() {
        List<Subject> subjects = Arrays.asList(
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()),
                TestDataFactory.makeSubject(TestDataFactory.randomUuid()));
        stubSyncSubjectsHelperCalls(subjects);

        mDataManager.syncSubjects().subscribe();
        // Verify right calls to helper methods
        verify(mMockRetrofitHelper).getRetrofitService().getSubjects();
        verify(mMockDatabaseHelper).setSubjects(subjects);
    }

    @Test
    public void syncSubjectsDoesNotCallDatabaseWhenApiFails() {
        when(mMockRetrofitHelper.getRetrofitService().getSubjects()
                .map(new Function<InTheatersEntity, List<Subject>>() {
                    @Override
                    public List<Subject> apply(InTheatersEntity inTheatersEntity) throws Exception {
                        return inTheatersEntity.subjects();
                    }
                })).thenReturn(Observable.<List<Subject>>error(new RuntimeException()));

        mDataManager.syncSubjects().subscribe(new TestObserver<Subject>());
        // Verify right calls to helper methods
        verify(mMockRetrofitHelper.getRetrofitService()).getSubjects();
        verify(mMockDatabaseHelper, never()).setSubjects(ArgumentMatchers.<Subject>anyList());
    }

    private void stubSyncSubjectsHelperCalls(List<Subject> subjects) {
        // Stub calls to the subject service and database helper.
        when(mMockRetrofitHelper.getRetrofitService().getSubjects()
                .map(new Function<InTheatersEntity, List<Subject>>() {
                    @Override
                    public List<Subject> apply(InTheatersEntity inTheatersEntity) throws Exception {
                        return inTheatersEntity.subjects();
                    }
                })).thenReturn(Observable.just(subjects));
        when(mMockDatabaseHelper.setSubjects(subjects))
                .thenReturn(Observable.fromIterable(subjects));
    }

}
