package uk.co.ribot.androidboilerplate.ui.main;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

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
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader;

@ConfigPersistent
public class MainViewModel extends BaseViewModel<MainMvvmView> {

    // These observable fields will update Views automatically
    public final ObservableList<Subject> items = new ObservableArrayList<>();

    private final DataManager mDataManager;

    private Subscription mSubscription;

    @Inject
    public MainViewModel(DataManager dataManager, ImageLoader imageLoader) {
        sImageLoader = imageLoader;
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
                        items.clear();
                        if (subjects.isEmpty()) {
                            getMvvmView().showSubjectsEmpty();
                        } else {
                            items.addAll(subjects);
                        }
                    }
                });
    }

    /*****
     * BindingAdapter
     *****/

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<Subject> items) {
        SubjectsAdapter adapter = (SubjectsAdapter) recyclerView.getAdapter();
        if (adapter != null)
            adapter.setSubjects(items);
    }

    /*****
     * Inner ViewModel
     *****/

    public static class SubjectViewModel extends BaseViewModel {

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
