package uk.co.ribot.androidboilerplate.data.model.pojo

import android.os.Parcelable

import com.google.auto.value.AutoValue
import com.google.gson.Gson
import com.google.gson.TypeAdapter

/**
 * Created by Zhou Zejin on 2016/9/14.
 */

@AutoValue
abstract class Person : Parcelable {

    abstract fun alt(): String
    abstract fun avatars(): Image
    abstract fun name(): String
    abstract fun id(): String

    companion object {
        fun create(alt: String, avatars: Image, name: String, id: String): Person {
            return AutoValue_Person(alt, avatars, name, id)
        }

        @JvmStatic
        fun typeAdapter(gson: Gson): TypeAdapter<Person> {
            return `$AutoValue_Person`.GsonTypeAdapter(gson)
        }
    }

}
