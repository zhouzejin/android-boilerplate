package uk.co.ribot.androidboilerplate.data.model.bean

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable

import com.gabrielittner.auto.value.cursor.ColumnAdapter
import com.google.auto.value.AutoValue
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.squareup.sqldelight.RowMapper
import com.sunny.sql.SubjectModel

import uk.co.ribot.androidboilerplate.data.model.pojo.Image
import uk.co.ribot.androidboilerplate.data.model.pojo.Person
import uk.co.ribot.androidboilerplate.data.model.pojo.Rating
import uk.co.ribot.androidboilerplate.utils.factory.MyColumnAdapterFactory
import uk.co.ribot.androidboilerplate.utils.factory.MyColumnTypeAdapterFactory

/**
 * Created by Zhou Zejin on 2016/9/21.
 */

@AutoValue
abstract class Subject : SubjectModel, Parcelable, Comparable<Subject> {

    abstract override fun id(): String

    @ColumnAdapter(MyColumnTypeAdapterFactory.RatingAdapter::class)
    abstract override fun rating(): Rating

    @ColumnAdapter(MyColumnTypeAdapterFactory.ListStringAdapter::class)
    abstract override fun genres(): List<String>

    abstract override fun title(): String

    @ColumnAdapter(MyColumnTypeAdapterFactory.ListPersonAdapter::class)
    abstract override fun casts(): List<Person>

    abstract override fun collect_count(): Int

    abstract override fun original_title(): String

    abstract override fun subtype(): String

    @ColumnAdapter(MyColumnTypeAdapterFactory.ListPersonAdapter::class)
    abstract override fun directors(): List<Person>

    abstract override fun year(): String

    @ColumnAdapter(MyColumnTypeAdapterFactory.ImageAdapter::class)
    abstract override fun images(): Image

    abstract override fun alt(): String

    @AutoValue.Builder
    abstract class Builder {
        abstract fun id(id: String): Builder
        abstract fun rating(rating: Rating): Builder
        abstract fun genres(genres: List<String>): Builder
        abstract fun title(title: String): Builder
        abstract fun casts(casts: MutableList<Person>): Builder
        abstract fun collect_count(collect_count: Int): Builder
        abstract fun original_title(original_title: String): Builder
        abstract fun subtype(subtype: String): Builder
        abstract fun directors(directors: MutableList<Person>): Builder
        abstract fun year(year: String): Builder
        abstract fun images(images: Image): Builder
        abstract fun alt(alt: String): Builder
        abstract fun build(): Subject
    }

    abstract fun toContentValues(): ContentValues

    override fun compareTo(other: Subject): Int {
        return title().compareTo(other.title())
    }

    companion object {
        val FACTORY: SubjectModel.Factory<Subject> = SubjectModel.Factory(
                SubjectModel.Creator<Subject> { id, rating, genres, title, casts, collect_count,
                                                original_title, subtype, directors, year, images, alt ->
                    AutoValue_Subject(id, rating, genres, title, casts, collect_count,
                            original_title, subtype, directors, year, images, alt)
                },
                MyColumnAdapterFactory.RATING_ADAPTER,
                MyColumnAdapterFactory.GENRES_ADAPTER,
                MyColumnAdapterFactory.PERSONS_ADAPTER,
                MyColumnAdapterFactory.PERSONS_ADAPTER,
                MyColumnAdapterFactory.IMAGE_ADAPTER)

        val MAPPER: RowMapper<Subject> = FACTORY.selectAllMapper()

        @JvmStatic
        fun create(cursor: Cursor): Subject {
            return `$$AutoValue_Subject`.createFromCursor(cursor)
        }

        @JvmStatic
        fun typeAdapter(gson: Gson): TypeAdapter<Subject> {
            return `$$$AutoValue_Subject`.GsonTypeAdapter(gson)
        }

        fun builder(): Builder {
            return `$$$$AutoValue_Subject`.Builder()
        }
    }

}
