package uk.co.ribot.androidboilerplate.utils.factory;

import android.content.ContentValues;
import android.database.Cursor;

import com.gabrielittner.auto.value.cursor.ColumnTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.pojo.Image;
import uk.co.ribot.androidboilerplate.data.model.pojo.Person;
import uk.co.ribot.androidboilerplate.data.model.pojo.Rating;

import static uk.co.ribot.androidboilerplate.utils.factory.MyGsonTypeAdapterFactory.GSON;

public class MyColumnTypeAdapterFactory {

    public static final class RatingAdapter implements ColumnTypeAdapter<Rating> {
        @Override
        public Rating fromCursor(Cursor cursor, String columnName) {
            String databaseValue = cursor.getString(cursor.getColumnIndex(columnName));
            return GSON.fromJson(databaseValue, Rating.class);
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Rating value) {
            String databaseValue = GSON.toJson(value);
            values.put(columnName, databaseValue);
        }
    }

    public static final class ListStringAdapter implements ColumnTypeAdapter<List<String>> {
        @Override
        public List<String> fromCursor(Cursor cursor, String columnName) {
            String databaseValue = cursor.getString(cursor.getColumnIndex(columnName));
            return GSON.fromJson(databaseValue, new TypeToken<List<String>>() {
            }.getType());
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, List<String> value) {
            String databaseValue = GSON.toJson(value);
            values.put(columnName, databaseValue);
        }
    }

    public static final class ListPersonAdapter implements ColumnTypeAdapter<List<Person>> {
        @Override
        public List<Person> fromCursor(Cursor cursor, String columnName) {
            String databaseValue = cursor.getString(cursor.getColumnIndex(columnName));
            return GSON.fromJson(databaseValue, new TypeToken<List<Person>>() {
            }.getType());
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, List<Person> value) {
            String databaseValue = GSON.toJson(value);
            values.put(columnName, databaseValue);
        }
    }

    public static final class ImageAdapter implements ColumnTypeAdapter<Image> {
        @Override
        public Image fromCursor(Cursor cursor, String columnName) {
            String databaseValue = cursor.getString(cursor.getColumnIndex(columnName));
            return GSON.fromJson(databaseValue, Image.class);
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Image value) {
            String databaseValue = GSON.toJson(value);
            values.put(columnName, databaseValue);
        }
    }

}
