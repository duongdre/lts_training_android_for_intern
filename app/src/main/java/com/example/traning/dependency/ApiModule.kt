package com.example.traning.dependency

import android.app.Application
import com.example.traning.BuildConfig
import com.example.traning.model.remote.ApiServiceAuth
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val size: Long = 100 * 1024 * 1024 // 100Mb
        return Cache(application.cacheDir, size)
    }

    @Provides
    @Singleton
    @Named("logger")
    fun provideLoggerInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        return logger
    }

    @Provides
    @Singleton
    @Named("header")
    fun provideHeaderInterceptor(app: Application): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                /*.header("Content-Type", "form-data")*/
                .header(
                    "app_info",
                    "Android${android.os.Build.VERSION.SDK_INT}_v${BuildConfig.VERSION_NAME}"
                )
                .method(original.method(), original.body())
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    @Named("header_no_auth")
    fun provideHeaderNoAuth(app: Application): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header(
                    "app_info",
                    "Android${android.os.Build.VERSION.SDK_INT}_v${BuildConfig.VERSION_NAME}"
                )
                .method(original.method(), original.body())
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideAuthenticator(application: Application): Authenticator {
        return Authenticator(application)
    }

    @Provides
    @Singleton
    @Named("auth_okhttp")
    fun provideOkHttpClient(
        auth: Authenticator,
        cache: Cache,
        @Named("logger") logger: Interceptor,
        @Named("header") header: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .authenticator(auth)
            .addInterceptor(header)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    @Named("no_auth_okhttp")
    fun provideNoAuthOkHttpClient(
        cache: Cache,
        @Named("logger") logger: Interceptor,
        @Named("header_no_auth") header: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(header)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    @Named("auth_retrofit")
    fun provideRetrofit(@Named("auth_okhttp") okHttpClient: OkHttpClient): Retrofit {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("no_auth_retrofit")
    fun provideNoAuthRetrofit(@Named("no_auth_okhttp") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiServiceNoAuth(@Named("no_auth_retrofit") retrofitNoAuthentication: Retrofit): ApiServiceAuth =
        retrofitNoAuthentication.create(ApiServiceAuth::class.java)

}
