package uk.co.ribot.androidboilerplate.utils.factory

import android.content.ContentValues
import android.database.Cursor

import com.gabrielittner.auto.value.cursor.ColumnTypeAdapter
import com.google.gson.reflect.TypeToken

import uk.co.ribot.androidboilerplate.data.model.pojo.Image
import uk.co.ribot.androidboilerplate.data.model.pojo.Person
import uk.co.ribot.androidboilerplate.data.model.pojo.Rating

import uk.co.ribot.androidboilerplate.utils.factory.MyGsonTypeAdapterFactory.Companion.GSON

class MyColumnTypeAdapterFactory {

    class RatingAdapter : ColumnTypeAdapter<Rating> {
        override fun fromCursor(cursor: Cursor, columnName: String): Rating {
            val databaseValue = cursor.getString(cursor.getColumnIndex(columnName))
            return GSON.fromJson(databaseValue, Rating::class.java)
        }

        override fun toContentValues(values: ContentValues, columnName: String, value: Rating) {
            val databaseValue = GSON.toJson(value)
            values.put(columnName, databaseValue)
        }
    }

    class ListStringAdapter : ColumnTypeAdapter<List<String>> {
        override fun fromCursor(cursor: Cursor, columnName: String): List<String>? {
            val databaseValue = cursor.getString(cursor.getColumnIndex(columnName))
            return GSON.fromJson<List<String>>(databaseValue, object : TypeToken<List<String>>() {

            }.type)
        }

        override fun toContentValues(values: ContentValues, columnName: String, value: List<String>) {
            val databaseValue = GSON.toJson(value)
            values.put(columnName, databaseValue)
        }
    }

    class ListPersonAdapter : ColumnTypeAdapter<List<Person>> {
        override fun fromCursor(cursor: Cursor, columnName: String): List<Person>? {
            val databaseValue = cursor.getString(cursor.getColumnIndex(columnName))
            return GSON.fromJson<List<Person>>(databaseValue, object : TypeToken<List<Person>>() {

            }.type)
        }

        override fun toContentValues(values: ContentValues, columnName: String, value: List<Person>) {
            val databaseValue = GSON.toJson(value)
            values.put(columnName, databaseValue)
        }
    }

    class ImageAdapter : ColumnTypeAdapter<Image> {
        override fun fromCursor(cursor: Cursor, columnName: String): Image {
            val databaseValue = cursor.getString(cursor.getColumnIndex(columnName))
            return GSON.fromJson(databaseValue, Image::class.java)
        }

        override fun toContentValues(values: ContentValues, columnName: String, value: Image) {
            val databaseValue = GSON.toJson(value)
            values.put(columnName, databaseValue)
        }
    }

}
