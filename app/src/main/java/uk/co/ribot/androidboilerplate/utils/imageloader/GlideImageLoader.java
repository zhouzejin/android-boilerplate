package uk.co.ribot.androidboilerplate.utils.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.IOException;
import java.io.InputStream;

import uk.co.ribot.androidboilerplate.utils.NetworkUtil;

/**
 * 开源框架图片加载框架Glide的封装实现
 * <p>
 * Created by Zhou Zejin on 2016/10/10.
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, ImageView imageView, String imageUrl,
                             DisplayOption option) {
        int strategy = option.getWifiStrategy();
        if (strategy == LOAD_STRATEGY_ONLY_WIFI) {
            int netType = NetworkUtil.getNetWorkType(context);
            // 如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
            if (netType == NetworkUtil.NETWORKTYPE_WIFI) {
                loadNormal(context, imageView, imageUrl, option);
            } else {
                // 如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
                loadCache(context, imageView, imageUrl, option);
            }
        } else {
            // 如果不是在wifi下才加载图片
            loadNormal(context, imageView, imageUrl, option);
        }
    }

    /**
     * load image with Glide
     */
    private void loadNormal(Context context, ImageView imageView, String imageUrl,
                            DisplayOption option) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(option.getPlaceHolder())
                .into(imageView);
    }

    /**
     * load cache image with Glide
     */
    private void loadCache(Context context, ImageView imageView, String imageUrl,
                           DisplayOption option) {
        Glide.with(context)
                .using(new StreamModelLoader<String>() {
                    @Override
                    public DataFetcher<InputStream> getResourceFetcher(final String model,
                                                                       int width, int height) {
                        return new DataFetcher<InputStream>() {
                            @Override
                            public InputStream loadData(Priority priority) throws Exception {
                                throw new IOException();
                            }

                            @Override
                            public void cleanup() {

                            }

                            @Override
                            public String getId() {
                                return model;
                            }

                            @Override
                            public void cancel() {

                            }
                        };
                    }
                })
                .load(imageUrl)
                .placeholder(option.getPlaceHolder())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
