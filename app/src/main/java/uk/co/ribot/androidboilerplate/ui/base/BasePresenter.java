package uk.co.ribot.androidboilerplate.ui.base;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvvmView that
 * can be accessed from the children classes by calling getMvvmView().
 */
public class BasePresenter<T extends MvvmView> implements Presenter<T> {

    private T mMvvmView;

    @Override
    public void attachView(T mvvmView) {
        mMvvmView = mvvmView;
    }

    @Override
    public void detachView() {
        mMvvmView = null;
    }

    public boolean isViewAttached() {
        return mMvvmView != null;
    }

    public T getMvvmView() {
        return mMvvmView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvvmViewNotAttachedException();
    }

    public static class MvvmViewNotAttachedException extends RuntimeException {
        public MvvmViewNotAttachedException() {
            super("Please call Presenter.attachView(MvvmView) before" +
                    " requesting data to the Presenter");
        }
    }
}
