package com.saad.base.data.di.daos

import com.saad.base.data.daos.remote.configuration.ConfigurationRemoteDao
import com.saad.base.data.daos.remote.user.UserRemoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RemoteDaosModule {


    @Singleton
    @Provides
    fun provideUserRemoteDao(
        retrofit: Retrofit
    ): UserRemoteDao {
        return retrofit.create(UserRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideConfigurationRemoteDao(
        retrofit: Retrofit
    ): ConfigurationRemoteDao {
        return retrofit.create(ConfigurationRemoteDao::class.java)
    }
}