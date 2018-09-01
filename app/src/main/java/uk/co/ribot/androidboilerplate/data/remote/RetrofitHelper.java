package uk.co.ribot.androidboilerplate.data.remote;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.ribot.androidboilerplate.BuildConfig;
import uk.co.ribot.androidboilerplate.injection.qualifier.ApplicationContext;
import uk.co.ribot.androidboilerplate.utils.NetworkUtil;
import uk.co.ribot.androidboilerplate.utils.factory.MyGsonTypeAdapterFactory;

@Singleton
public class RetrofitHelper {

    private static final int DEFAULT_TIMEOUT = 7;

    private static OkHttpClient sHttpClient;
    private static OkHttpClient.Builder sHttpClientBuilder = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true);

    static {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            sHttpClientBuilder.addInterceptor(logging);
        }
    }

    private static void initOkHttpClient(@ApplicationContext final Context context) {
        synchronized (RetrofitHelper.class) {
            // 缓存文件设置
            final String CACHE_FILE_NAME = "RetrofitHttpCache";
            final long CACHE_SIZE = 1024 * 1024 * 100;
            final Cache CACHE_FILE = new Cache(new File(context.getCacheDir(), CACHE_FILE_NAME),
                    CACHE_SIZE);

            // 设置HTTP缓存拦截器
            final int CACHE_TIME = 60 * 60 * 24 * 7;
            final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetworkUtil.isNetworkConnected(context)) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }

                    Response originalResponse = chain.proceed(request);
                    if (NetworkUtil.isNetworkConnected(context)) {
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale="
                                        + CACHE_TIME)
                                .removeHeader("Pragma")
                                .build();
                    }
                }
            };

            // REWRITE_CACHE_CONTROL_INTERCEPTOR拦截器需要同时设置networkInterceptors和interceptors
            sHttpClient = sHttpClientBuilder
                    .cache(CACHE_FILE)
                    .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .build();
        }
    }

    private static <T> T createApiService(@ApplicationContext Context context, Class<T> clazz,
                                          String baseUrl) {
        initOkHttpClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(sHttpClient)
                .addConverterFactory(GsonConverterFactory.create(MyGsonTypeAdapterFactory.GSON))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    private RetrofitService mRetrofitService;

    @Inject
    public RetrofitHelper(@ApplicationContext Context context) {
        mRetrofitService = createApiService(context, RetrofitService.class, RetrofitService.ENDPOINT);
    }

    public RetrofitService getRetrofitService() {
        return mRetrofitService;
    }

}
