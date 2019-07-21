package com.cryptoworld.project.jvr.bitcoinstats.di

import com.cryptoworld.project.jvr.bitcoinstats.network.BitcoinApi
import com.cryptoworld.project.jvr.bitcoinstats.utils.AppSchedulerProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module

object BitcoinAppModule {
    private val BASE_URL = "https://api.blockchain.info/charts/"
    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BASIC }
    private val client : OkHttpClient = OkHttpClient.Builder().apply { this.addInterceptor(interceptor) }.build()


    @Provides
    @Reusable
    @JvmStatic
    internal fun bitcoinApiProvider(retrofit: Retrofit): BitcoinApi {
        return retrofit.create(BitcoinApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun retrofitInterfaceProvider(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideSchedulerProvider() : AppSchedulerProvider{
        return AppSchedulerProvider()
    }





}