package uk.co.ribot.androidboilerplate.ui.base;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvvmView type that wants to be attached with.
 */
public interface Presenter<V extends MvvmView> {

    void attachView(V mvvmView);

    void detachView();
}
