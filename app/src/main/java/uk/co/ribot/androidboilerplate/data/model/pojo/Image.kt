package uk.co.ribot.androidboilerplate.data.model.pojo

import android.os.Parcelable

import com.google.auto.value.AutoValue
import com.google.gson.Gson
import com.google.gson.TypeAdapter

/**
 * Created by Zhou Zejin on 2016/9/14.
 */

@AutoValue
abstract class Image : Parcelable {

    abstract fun small(): String
    abstract fun large(): String
    abstract fun medium(): String

    companion object {
        fun create(small: String, large: String, medium: String): Image {
            return AutoValue_Image(small, large, medium)
        }

        @JvmStatic
        fun typeAdapter(gson: Gson): TypeAdapter<Image> {
            return `$AutoValue_Image`.GsonTypeAdapter(gson)
        }
    }

}
