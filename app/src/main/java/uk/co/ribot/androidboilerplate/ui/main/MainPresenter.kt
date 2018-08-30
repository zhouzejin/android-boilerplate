package uk.co.ribot.androidboilerplate.ui.main

import javax.inject.Inject

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uk.co.ribot.androidboilerplate.data.DataManager
import uk.co.ribot.androidboilerplate.data.model.bean.Subject
import uk.co.ribot.androidboilerplate.injection.scope.ConfigPersistent
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter
import uk.co.ribot.androidboilerplate.utils.*

@ConfigPersistent
class MainPresenter @Inject constructor(private val mDataManager: DataManager)
    : BasePresenter<MainMvpView>() {

    private var mDisposable: Disposable? = null

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        dispose(mDisposable)
    }

    fun loadSubjects() {
        checkViewAttached()
        dispose(mDisposable)
        mDataManager.getSubjects()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<Subject>> {
                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                    override fun onComplete() {
                        
                    }

                    override fun onError(e: Throwable) {
                        e(e, "There was an error loading the subjects.")
                        getMvpView()!!.showError()
                    }

                    override fun onNext(subjects: List<Subject>) {
                        if (subjects.isEmpty()) {
                            getMvpView()!!.showSubjectsEmpty()
                        } else {
                            getMvpView()!!.showSubjects(subjects)
                        }
                    }
                })
    }

}
