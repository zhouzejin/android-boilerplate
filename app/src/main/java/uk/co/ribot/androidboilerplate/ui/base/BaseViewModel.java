package uk.co.ribot.androidboilerplate.ui.base;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader;

/**
 * Base class that implements the ViewModel interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvvmView that
 * can be accessed from the children classes by calling getMvvmView().
 */
public class BaseViewModel<T extends MvvmView> extends BaseObservable implements ViewModel<T> {

    private T mMvvmView;

    protected static ImageLoader sImageLoader;

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
            super("Please call ViewModel.attachView(MvvmView) before" +
                    " requesting data to the ViewModel");
        }
    }

    /*****
     * BindingAdapter
     *****/
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder().build();
        if (sImageLoader != null)
            sImageLoader.displayImage(imageView.getContext(), imageView, imageUrl, option);
    }

}
