package com.raantech.solalat.user.data.di

import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.raantech.solalat.user.data.common.NetworkConstants.APP_BASE_URL
import com.raantech.solalat.user.data.common.NetworkConstants.APP_TIMEOUT_MINUTES
import com.raantech.solalat.user.data.interceptors.AppBaseInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import com.raantech.solalat.user.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

//    @Provides
//    @Singleton
//    fun provideInterceptor(
//        configurationPref: ConfigurationPref,
//        userPref: UserPref
//    ): AppBaseInterceptor = AppBaseInterceptor(
//        configurationPref,
//        userPref
//    )

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        interceptor: AppBaseInterceptor,
        loggerInterceptor: HttpLoggingInterceptor,
        application: Application
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .readTimeout(APP_TIMEOUT_MINUTES, TimeUnit.MINUTES)
            .writeTimeout(APP_TIMEOUT_MINUTES, TimeUnit.MINUTES)
            .connectTimeout(APP_TIMEOUT_MINUTES, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG)
            okHttpClient
                .addInterceptor(ChuckInterceptor(application))
                .addInterceptor(loggerInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                    request.addHeader("Accept", "application/json")

                    val response = chain.proceed(request.build())
                    if (response.code == 401) {
                        EventBus.getDefault().post(response.code.toString())
                    }

                    response

                }

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(APP_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}