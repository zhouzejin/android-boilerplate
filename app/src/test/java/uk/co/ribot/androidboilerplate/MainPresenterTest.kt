package uk.co.ribot.androidboilerplate

import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.test.common.TestDataFactory
import uk.co.ribot.androidboilerplate.ui.main.MainMvpView
import uk.co.ribot.androidboilerplate.ui.main.MainPresenter
import uk.co.ribot.androidboilerplate.utils.RxSchedulersOverrideRule

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    internal lateinit var mMockMainMvpView: MainMvpView
    @Mock
    internal lateinit var mMockDataManager: DataManager

    private lateinit var mMainPresenter: MainPresenter

    @Rule
    @JvmField
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        mMainPresenter = MainPresenter(mMockDataManager)
        mMainPresenter.attachView(mMockMainMvpView)
    }

    @After
    fun tearDown() {
        mMainPresenter.detachView()
    }

    @Test
    fun loadSubjectsReturnsSubjects() {
        val subjects = TestDataFactory.makeListSubject(10)
        `when`(mMockDataManager.getSubjects())
                .thenReturn(Observable.just(subjects))

        mMainPresenter.loadSubjects()
        verify<MainMvpView>(mMockMainMvpView).showSubjects(subjects)
        verify<MainMvpView>(mMockMainMvpView, never()).showSubjectsEmpty()
        verify<MainMvpView>(mMockMainMvpView, never()).showError()
    }

    @Test
    fun loadSubjectsReturnsEmptyList() {
        `when`(mMockDataManager.getSubjects())
                .thenReturn(Observable.just(emptyList()))

        mMainPresenter.loadSubjects()
        verify<MainMvpView>(mMockMainMvpView).showSubjectsEmpty()
        verify<MainMvpView>(mMockMainMvpView, never()).showSubjects(ArgumentMatchers.anyList())
        verify<MainMvpView>(mMockMainMvpView, never()).showError()
    }

    @Test
    fun loadSubjectsFails() {
        `when`(mMockDataManager.getSubjects())
                .thenReturn(Observable.error(RuntimeException()))

        mMainPresenter.loadSubjects()
        verify<MainMvpView>(mMockMainMvpView).showError()
        verify<MainMvpView>(mMockMainMvpView, never()).showSubjectsEmpty()
        verify<MainMvpView>(mMockMainMvpView, never()).showSubjects(ArgumentMatchers.anyList())
    }

}
