package com.sunny.datalayer.model.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.gabrielittner.auto.value.cursor.ColumnAdapter;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.squareup.sqldelight.RowMapper;
import com.sunny.datalayer.factory.MyColumnAdapterFactory;
import com.sunny.datalayer.factory.MyColumnTypeAdapterFactory;
import com.sunny.datalayer.model.pojo.Image;
import com.sunny.datalayer.model.pojo.Person;
import com.sunny.datalayer.model.pojo.Rating;
import com.sunny.sql.SubjectModel;

import java.util.List;

/**
 * Created by Zhou Zejin on 2016/9/21.
 */

@AutoValue
public abstract class Subject implements SubjectModel, Parcelable, Comparable<Subject> {

    @NonNull
    @Override
    public abstract String id();

    @NonNull
    @ColumnAdapter(MyColumnTypeAdapterFactory.RatingAdapter.class)
    @Override
    public abstract Rating rating();

    @NonNull
    @ColumnAdapter(MyColumnTypeAdapterFactory.ListStringAdapter.class)
    @Override
    public abstract List<String> genres();

    @NonNull
    @Override
    public abstract String title();

    @NonNull
    @ColumnAdapter(MyColumnTypeAdapterFactory.ListPersonAdapter.class)
    @Override
    public abstract List<Person> casts();

    @Override
    public abstract int collect_count();

    @NonNull
    @Override
    public abstract String original_title();

    @NonNull
    @Override
    public abstract String subtype();

    @NonNull
    @ColumnAdapter(MyColumnTypeAdapterFactory.ListPersonAdapter.class)
    @Override
    public abstract List<Person> directors();

    @NonNull
    @Override
    public abstract String year();

    @NonNull
    @ColumnAdapter(MyColumnTypeAdapterFactory.ImageAdapter.class)
    @Override
    public abstract Image images();

    @NonNull
    @Override
    public abstract String alt();

    public static final Factory<Subject> FACTORY = new Factory<>(
            new SubjectModel.Creator<Subject>() {
                @Override
                public Subject create(@NonNull String id, @NonNull Rating rating,
                                      @NonNull List<String> genres, @NonNull String title,
                                      @NonNull List<Person> casts, int collect_count,
                                      @NonNull String original_title, @NonNull String subtype,
                                      @NonNull List<Person> directors, @NonNull String year,
                                      @NonNull Image images, @NonNull String alt) {
                    return new AutoValue_Subject(id, rating, genres, title, casts, collect_count,
                            original_title, subtype, directors, year, images, alt);
                }
            },
            MyColumnAdapterFactory.RATING_ADAPTER,
            MyColumnAdapterFactory.GENRES_ADAPTER,
            MyColumnAdapterFactory.PERSONS_ADAPTER,
            MyColumnAdapterFactory.PERSONS_ADAPTER,
            MyColumnAdapterFactory.IMAGE_ADAPTER);

    public static final RowMapper<Subject> MAPPER = FACTORY.selectAllMapper();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder rating(Rating rating);

        public abstract Builder genres(List<String> genres);

        public abstract Builder title(String title);

        public abstract Builder casts(List<Person> casts);

        public abstract Builder collect_count(int collect_count);

        public abstract Builder original_title(String original_title);

        public abstract Builder subtype(String subtype);

        public abstract Builder directors(List<Person> directors);

        public abstract Builder year(String year);

        public abstract Builder images(Image images);

        public abstract Builder alt(String alt);

        public abstract Subject build();
    }

    public static Builder builder() {
        return new AutoValue_Subject.Builder();
    }

    public static TypeAdapter<Subject> typeAdapter(Gson gson) {
        return new AutoValue_Subject.GsonTypeAdapter(gson);
    }

    public static Subject create(Cursor cursor) {
        return AutoValue_Subject.createFromCursor(cursor);
    }

    public abstract ContentValues toContentValues();

    @Override
    public int compareTo(@NonNull Subject another) {
        return title().compareTo(another.title());
    }

}
