package uk.co.ribot.androidboilerplate.utils.factory

import com.google.gson.TypeAdapterFactory
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory

@GsonTypeAdapterFactory
abstract class MyGsonTypeAdapterFactory : TypeAdapterFactory {
    companion object {
        fun create(): TypeAdapterFactory {
            return AutoValueGson_MyGsonTypeAdapterFactory()
        }
    }
}
