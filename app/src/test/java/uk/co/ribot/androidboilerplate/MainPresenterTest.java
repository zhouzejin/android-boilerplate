package uk.co.ribot.androidboilerplate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory;
import uk.co.ribot.androidboilerplate.ui.main.MainMvpView;
import uk.co.ribot.androidboilerplate.ui.main.MainPresenter;
import uk.co.ribot.androidboilerplate.utils.RxSchedulersOverrideRule;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock MainMvpView mMockMainMvpView;
    @Mock DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadSubjectsReturnsSubjects() {
        List<Subject> subjects = TestDataFactory.makeListSubject(10);
        when(mMockDataManager.getSubjects())
                .thenReturn(Observable.just(subjects));

        mMainPresenter.loadSubjects();
        verify(mMockMainMvpView).showSubjects(subjects);
        verify(mMockMainMvpView, never()).showSubjectsEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadSubjectsReturnsEmptyList() {
        when(mMockDataManager.getSubjects())
                .thenReturn(Observable.just(Collections.<Subject>emptyList()));

        mMainPresenter.loadSubjects();
        verify(mMockMainMvpView).showSubjectsEmpty();
        verify(mMockMainMvpView, never()).showSubjects(anyListOf(Subject.class));
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadSubjectsFails() {
        when(mMockDataManager.getSubjects())
                .thenReturn(Observable.<List<Subject>>error(new RuntimeException()));

        mMainPresenter.loadSubjects();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showSubjectsEmpty();
        verify(mMockMainMvpView, never()).showSubjects(anyListOf(Subject.class));
    }

}
