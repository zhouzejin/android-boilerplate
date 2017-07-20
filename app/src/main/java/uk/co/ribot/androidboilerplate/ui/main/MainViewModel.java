package uk.co.ribot.androidboilerplate.ui.main;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.injection.scope.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BaseViewModel;
import uk.co.ribot.androidboilerplate.utils.LogUtil;
import uk.co.ribot.androidboilerplate.utils.RxUtil;

@ConfigPersistent
public class MainViewModel extends BaseViewModel<MainMvvmView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainViewModel(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvvmView mvvmView) {
        super.attachView(mvvmView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadSubjects() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getSubjects()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Subject>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e, "There was an error loading the subjects.");
                        getMvvmView().showError();
                    }

                    @Override
                    public void onNext(List<Subject> subjects) {
                        if (subjects.isEmpty()) {
                            getMvvmView().showSubjectsEmpty();
                        } else {
                            getMvvmView().showSubjects(subjects);
                        }
                    }
                });
    }

}
