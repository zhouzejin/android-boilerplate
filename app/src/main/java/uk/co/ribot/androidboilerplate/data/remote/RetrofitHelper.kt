package uk.co.ribot.androidboilerplate.data.remote

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.ribot.androidboilerplate.BuildConfig
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext
import uk.co.ribot.androidboilerplate.utils.factory.MyGsonTypeAdapterFactory
import uk.co.ribot.androidboilerplate.utils.isNetworkConnected
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitHelper @Inject constructor(@ApplicationContext context: Context) {

    val retrofitService: RetrofitService

    init {
        retrofitService = createApiService(context, RetrofitService::class.java, RetrofitService.ENDPOINT)
    }

    companion object {

        private const val DEFAULT_TIMEOUT = 7

        private var sHttpClient: OkHttpClient? = null
        private val sHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
        private val sGson = GsonBuilder()
                .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()

        init {
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                sHttpClientBuilder.addInterceptor(logging)
            }
        }

        private fun initOkHttpClient(@ApplicationContext context: Context) {
            synchronized(RetrofitHelper::class.java) {
                // 缓存文件设置
                val CACHE_FILE_NAME = "RetrofitHttpCache"
                val CACHE_SIZE = (1024 * 1024 * 100).toLong()
                val CACHE_FILE = Cache(File(context.cacheDir, CACHE_FILE_NAME), CACHE_SIZE)

                // 设置HTTP缓存拦截器
                val CACHE_TIME = 60 * 60 * 24 * 7
                val REWRITE_CACHE_CONTROL_INTERCEPTOR = Interceptor { chain ->
                    var request = chain.request()
                    if (!isNetworkConnected(context)) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build()
                    }

                    val originalResponse = chain.proceed(request)
                    if (isNetworkConnected(context)) {
                        val cacheControl = request.cacheControl().toString()
                        originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build()
                    } else {
                        originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=$CACHE_TIME")
                                .removeHeader("Pragma")
                                .build()
                    }
                }

                // REWRITE_CACHE_CONTROL_INTERCEPTOR拦截器需要同时设置networkInterceptors和interceptors
                sHttpClient = sHttpClientBuilder
                        .cache(CACHE_FILE)
                        .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                        .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                        .build()
            }
        }

        private fun <T> createApiService(@ApplicationContext context: Context, clazz: Class<T>,
                                         baseUrl: String): T {
            initOkHttpClient(context)
            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(sHttpClient!!)
                    .addConverterFactory(GsonConverterFactory.create(sGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(clazz)
        }
    }

}
