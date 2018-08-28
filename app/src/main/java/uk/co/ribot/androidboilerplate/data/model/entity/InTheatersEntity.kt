package uk.co.ribot.androidboilerplate.data.model.entity

import android.os.Parcelable

import com.google.auto.value.AutoValue
import com.google.gson.Gson
import com.google.gson.TypeAdapter

import uk.co.ribot.androidboilerplate.data.model.bean.Subject

/**
 * Created by Zhou Zejin on 2016/9/12.
 */

@AutoValue
abstract class InTheatersEntity : Parcelable {

    abstract fun count(): Int
    abstract fun start(): Int
    abstract fun total(): Int
    abstract fun title(): String
    abstract fun subjects(): List<Subject>

    companion object {
        fun create(count: Int, start: Int, total: Int,
                   title: String, subjects: List<Subject>): InTheatersEntity {
            return AutoValue_InTheatersEntity(count, start, total, title, subjects)
        }

        @JvmStatic
        fun typeAdapter(gson: Gson): TypeAdapter<InTheatersEntity> {
            return `$AutoValue_InTheatersEntity`.GsonTypeAdapter(gson)
        }
    }

}
