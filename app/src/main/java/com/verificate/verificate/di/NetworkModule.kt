package com.verificate.verificate.di


import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.verificate.verificate.BuildConfig
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.base.getAuthorization
import com.verificate.verificate.framework.datasource.remote.ApiService
import com.verificate.verificate.framework.datasource.remote.StatesServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val NETWORK_BASE_URL = "https://utpqlc1pqh.execute-api.eu-west-1.amazonaws.com/dev/"
    const val NETWORK_BASE_URL2 = "http://states-and-cities.com/api/v1/"

    @Provides
    @Singleton
    fun  provideNetworkURL():String = NETWORK_BASE_URL

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    private fun okHttpClient(localStorage: LocalStorage, logger: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(headersInterceptor(localStorage))
        .addInterceptor((logger))
        .readTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(  5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .build()

    private fun headersInterceptor(localStorage: LocalStorage) = Interceptor { chain ->
        chain.proceed(chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", localStorage.getAuthorization())
            .build())
    }

    @Provides
    @Singleton
    @Named("MainAPI")
    fun  provideRetrofit(logger: HttpLoggingInterceptor, localStorage: LocalStorage): Retrofit =
        Retrofit.Builder().
        baseUrl(provideNetworkURL()).
        client(okHttpClient(localStorage,logger)).
        addCallAdapterFactory(CoroutineCallAdapterFactory()).
        addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())).build()

    @Provides
    @Singleton
    fun provideAPIService(@Named("MainAPI") provideRetrofit: Retrofit): ApiService = provideRetrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideStateAPIService(@Named("StateAPI")provideAltRetrofit: Retrofit): StatesServiceApi = provideAltRetrofit.create(StatesServiceApi::class.java)

    @Provides
    @Singleton
    @Named("StateAPI")
    fun  provideAltRetrofit(logger: HttpLoggingInterceptor, localStorage: LocalStorage): Retrofit =
        Retrofit.Builder().
        baseUrl(NETWORK_BASE_URL2).
        client(okHttpClient(localStorage,logger)).
        addCallAdapterFactory(CoroutineCallAdapterFactory()).
        addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())).build()

}
