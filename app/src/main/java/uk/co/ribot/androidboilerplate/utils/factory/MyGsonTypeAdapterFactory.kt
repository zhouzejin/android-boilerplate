package uk.co.ribot.androidboilerplate.utils.factory

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory

@GsonTypeAdapterFactory
abstract class MyGsonTypeAdapterFactory : TypeAdapterFactory {
    companion object {
        fun create(): TypeAdapterFactory {
            return AutoValueGson_MyGsonTypeAdapterFactory()
        }

        val GSON = GsonBuilder()
                .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()
    }
}
