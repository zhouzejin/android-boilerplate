package uk.co.ribot.androidboilerplate.data.model.pojo

import android.os.Parcelable

import com.google.auto.value.AutoValue
import com.google.gson.Gson
import com.google.gson.TypeAdapter

/**
 * Created by Zhou Zejin on 2016/9/14.
 */

@AutoValue
abstract class Rating : Parcelable {

    abstract fun max(): Int
    abstract fun average(): Double
    abstract fun stars(): String
    abstract fun min(): Int

    companion object {
        fun create(max: Int, average: Double, stars: String, min: Int): Rating {
            return AutoValue_Rating(max, average, stars, min)
        }

        @JvmStatic
        fun typeAdapter(gson: Gson): TypeAdapter<Rating> {
            return `$AutoValue_Rating`.GsonTypeAdapter(gson)
        }
    }

}
