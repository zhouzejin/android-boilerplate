package com.sunny.main.ui.main;

import com.sunny.common.injection.scope.ConfigPersistent;
import com.sunny.common.utils.LogUtil;
import com.sunny.common.utils.RxUtil;
import com.sunny.commonbusiness.base.BasePresenter;
import com.sunny.datalayer.model.bean.Subject;
import com.sunny.main.data.DataManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void loadSubjects() {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.getSubjects()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Subject>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "There was an error loading the subjects.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Subject> subjects) {
                        if (subjects.isEmpty()) {
                            getMvpView().showSubjectsEmpty();
                        } else {
                            getMvpView().showSubjects(subjects);
                        }
                    }
                });
    }

}
