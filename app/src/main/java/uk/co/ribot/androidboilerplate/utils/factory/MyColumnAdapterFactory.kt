package uk.co.ribot.androidboilerplate.utils.factory

import com.google.gson.reflect.TypeToken
import com.squareup.sqldelight.ColumnAdapter

import uk.co.ribot.androidboilerplate.data.model.pojo.Image
import uk.co.ribot.androidboilerplate.data.model.pojo.Person
import uk.co.ribot.androidboilerplate.data.model.pojo.Rating
import uk.co.ribot.androidboilerplate.utils.factory.MyGsonTypeAdapterFactory.Companion.GSON

object MyColumnAdapterFactory {

    val RATING_ADAPTER: ColumnAdapter<Rating, String> = object : ColumnAdapter<Rating, String> {
        override fun decode(databaseValue: String): Rating {
            return GSON.fromJson(databaseValue, Rating::class.java)
        }

        override fun encode(value: Rating): String {
            return GSON.toJson(value)
        }
    }

    val GENRES_ADAPTER: ColumnAdapter<List<String>, String> = object : ColumnAdapter<List<String>, String> {
        override fun decode(databaseValue: String): List<String> {
            return GSON.fromJson(databaseValue, object : TypeToken<List<String>>() {}.type)
        }

        override fun encode(value: List<String>): String {
            return GSON.toJson(value)
        }
    }

    val PERSONS_ADAPTER: ColumnAdapter<List<Person>, String> = object : ColumnAdapter<List<Person>, String> {
        override fun decode(databaseValue: String): List<Person> {
            return GSON.fromJson(databaseValue, object : TypeToken<List<Person>>() {}.type)
        }

        override fun encode(value: List<Person>): String {
            return GSON.toJson(value)
        }
    }

    val IMAGE_ADAPTER: ColumnAdapter<Image, String> = object : ColumnAdapter<Image, String> {
        override fun decode(databaseValue: String): Image {
            return GSON.fromJson(databaseValue, Image::class.java)
        }

        override fun encode(value: Image): String {
            return GSON.toJson(value)
        }
    }

}
