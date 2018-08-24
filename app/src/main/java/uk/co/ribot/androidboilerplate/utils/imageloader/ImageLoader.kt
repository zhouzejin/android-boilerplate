package uk.co.ribot.androidboilerplate.utils.imageloader

import android.content.Context
import android.widget.ImageView

import uk.co.ribot.androidboilerplate.R

/**
 * 图片加载功能抽象类
 *
 * Created by Zhou Zejin on 2016/10/10.
 */

interface ImageLoader {

    companion object {
        /**
         * 可以使用Enum进行封装
         */
        const val PIC_LARGE = 0
        const val PIC_MEDIUM = 1
        const val PIC_SMALL = 2

        /**
         * 可以使用Enum进行封装
         */
        const val LOAD_STRATEGY_NORMAL = 0
        const val LOAD_STRATEGY_ONLY_WIFI = 1
    }

    /**
     * 图片加载参数
     *
     * 使用Builder设计模式
     */
    class DisplayOption private constructor(builder: Builder) {
        /**
         * 类型 (大图，中图，小图)
         */
        val type: Int
        /**
         * 当没有成功加载的时候显示的图片
         */
        val placeHolder: Int
        /**
         * 加载策略，是否在wifi下才加载
         */
        val wifiStrategy: Int

        init {
            this.type = builder.type
            this.placeHolder = builder.placeHolder
            this.wifiStrategy = builder.wifiStrategy
        }

        class Builder {
            var type: Int = 0
                private set
            var placeHolder: Int = 0
                private set
            var wifiStrategy: Int = 0
                private set

            init {
                this.type = PIC_SMALL
                this.placeHolder = R.mipmap.ic_launcher
                this.wifiStrategy = LOAD_STRATEGY_NORMAL
            }

            fun type(type: Int): Builder {
                this.type = type
                return this
            }

            fun placeHolder(placeHolder: Int): Builder {
                this.placeHolder = placeHolder
                return this
            }

            fun strategy(strategy: Int): Builder {
                this.wifiStrategy = strategy
                return this
            }

            fun build(): DisplayOption {
                return DisplayOption(this)
            }
        }
    }

    /**
     * 显示图片
     *
     * @param context ImageView的Context
     * @param imageView 显示图片的ImageView
     * @param imageUrl  图片资源的URL
     * @param option    显示参数设置
     */
    fun displayImage(context: Context, imageView: ImageView, imageUrl: String, option: DisplayOption)

}
