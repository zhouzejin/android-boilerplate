package uk.co.ribot.androidboilerplate.ui.main;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.bean.Subject;
import uk.co.ribot.androidboilerplate.injection.scope.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BaseViewModel;
import uk.co.ribot.androidboilerplate.utils.LogUtil;
import uk.co.ribot.androidboilerplate.utils.RxUtil;

@ConfigPersistent
public class MainViewModel extends BaseViewModel<MainMvvmView> {

    // These observable fields will update Views automatically
    public final ObservableList<Subject> items = new ObservableArrayList<>();

    private final DataManager mDataManager;

    private Disposable mDisposable;

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
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Subject> subjects) {
                        items.clear();
                        if (subjects.isEmpty()) {
                            getMvvmView().showSubjectsEmpty();
                        } else {
                            items.addAll(subjects);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(e, "There was an error loading the subjects.");
                        getMvvmView().showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*****
     * Inner ViewModel
     *****/

    public class SubjectViewModel extends BaseViewModel {

        public final ObservableField<String> title = new ObservableField<>();
        public final ObservableField<String> genres = new ObservableField<>();
        public final ObservableField<String> imageUrl = new ObservableField<>();

        public SubjectViewModel(Subject subject) {
            title.set(subject.title());
            genres.set(subject.genres().toString());
            imageUrl.set(subject.images().small());
        }
    }

}
