package com.jayeshsolanki.olaplaystudios.tool.dagger.module

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal class DataModule(private val baseUrl: String) {

    companion object {
        private val CACHE_SIZE = 50 * 1024 * 1024 * 1L // 50 MB
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(context: Context) =
            PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    internal fun provideHttpCache(context: Context) = Cache(context.cacheDir, CACHE_SIZE)

    @Provides
    @Singleton
    internal fun provideGson() = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache) = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

}
