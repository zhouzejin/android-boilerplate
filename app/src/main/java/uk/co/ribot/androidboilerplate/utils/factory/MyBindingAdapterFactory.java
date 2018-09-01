package uk.co.ribot.androidboilerplate.utils.factory;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import uk.co.ribot.androidboilerplate.ui.base.BaseAdapter;
import uk.co.ribot.androidboilerplate.utils.imageloader.GlideImageLoader;
import uk.co.ribot.androidboilerplate.utils.imageloader.ImageLoader;

public class MyBindingAdapterFactory {

    private final static ImageLoader sImageLoader = new GlideImageLoader();

    /*****
     * BindingAdapter
     *****/

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, List<T> items) {
        BaseAdapter<T> adapter = (BaseAdapter<T>) recyclerView.getAdapter();
        if (adapter != null)
            adapter.setData(items);
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        ImageLoader.DisplayOption option = new ImageLoader.DisplayOption.Builder().build();
        sImageLoader.displayImage(imageView.getContext(), imageView, imageUrl, option);
    }

}
