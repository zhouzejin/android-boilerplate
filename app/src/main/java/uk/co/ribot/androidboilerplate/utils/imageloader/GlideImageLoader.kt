package uk.co.ribot.androidboilerplate.utils.imageloader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import uk.co.ribot.androidboilerplate.utils.NETWORKTYPE_WIFI
import uk.co.ribot.androidboilerplate.utils.getNetWorkType

/**
 * 开源框架图片加载框架Glide的封装实现
 *
 * Created by Zhou Zejin on 2016/10/10.
 */

open class GlideImageLoader : ImageLoader {

    override fun displayImage(context: Context, imageView: ImageView, imageUrl: String,
                              option: ImageLoader.DisplayOption) {
        val strategy = option.wifiStrategy
        if (strategy == ImageLoader.LOAD_STRATEGY_ONLY_WIFI) {
            val netType = getNetWorkType(context)
            // 如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
            if (netType == NETWORKTYPE_WIFI) {
                loadNormal(context, imageView, imageUrl, option)
            } else {
                // 如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
                loadCache(context, imageView, imageUrl, option)
            }
        } else {
            // 如果不是在wifi下才加载图片
            loadNormal(context, imageView, imageUrl, option)
        }
    }

    /**
     * load image with Glide
     */
    private fun loadNormal(context: Context, imageView: ImageView, imageUrl: String,
                           option: ImageLoader.DisplayOption) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(option.placeHolder)
                .into(imageView)
    }

    /**
     * load cache image with Glide
     */
    private fun loadCache(context: Context, imageView: ImageView, imageUrl: String,
                          option: ImageLoader.DisplayOption) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(option.placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

}
