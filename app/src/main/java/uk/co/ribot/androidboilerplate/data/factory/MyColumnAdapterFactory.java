package uk.co.ribot.androidboilerplate.data.factory;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.squareup.sqldelight.ColumnAdapter;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.pojo.Image;
import uk.co.ribot.androidboilerplate.data.model.pojo.Person;
import uk.co.ribot.androidboilerplate.data.model.pojo.Rating;

import static uk.co.ribot.androidboilerplate.data.factory.MyGsonTypeAdapterFactory.GSON;

public class MyColumnAdapterFactory {

    public static final ColumnAdapter<Rating, String> RATING_ADAPTER = new ColumnAdapter<Rating, String>() {
        @NonNull
        @Override
        public Rating decode(String databaseValue) {
            return GSON.fromJson(databaseValue, Rating.class);
        }

        @Override
        public String encode(@NonNull Rating value) {
            return GSON.toJson(value);
        }
    };

    public static final ColumnAdapter<List<String>, String> GENRES_ADAPTER = new ColumnAdapter<List<String>, String>() {
        @NonNull
        @Override
        public List<String> decode(String databaseValue) {
            return GSON.fromJson(databaseValue, new TypeToken<List<String>>() {
            }.getType());
        }

        @Override
        public String encode(@NonNull List<String> value) {
            return GSON.toJson(value);
        }
    };

    public static final ColumnAdapter<List<Person>, String> PERSONS_ADAPTER = new ColumnAdapter<List<Person>, String>() {
        @NonNull
        @Override
        public List<Person> decode(String databaseValue) {
            return GSON.fromJson(databaseValue, new TypeToken<List<Person>>() {
            }.getType());
        }

        @Override
        public String encode(@NonNull List<Person> value) {
            return GSON.toJson(value);
        }
    };

    public static final ColumnAdapter<Image, String> IMAGE_ADAPTER = new ColumnAdapter<Image, String>() {
        @NonNull
        @Override
        public Image decode(String databaseValue) {
            return GSON.fromJson(databaseValue, Image.class);
        }

        @Override
        public String encode(@NonNull Image value) {
            return GSON.toJson(value);
        }
    };

}
