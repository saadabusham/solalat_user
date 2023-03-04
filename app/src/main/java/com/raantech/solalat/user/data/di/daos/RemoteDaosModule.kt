package com.raantech.solalat.user.data.di.daos

import com.raantech.solalat.user.data.daos.remote.accessories.AccessoriesRemoteDao
import com.raantech.solalat.user.data.daos.remote.barn.BarnRemoteDao
import com.raantech.solalat.user.data.daos.remote.configuration.ConfigurationRemoteDao
import com.raantech.solalat.user.data.daos.remote.filter.FilterRemoteDao
import com.raantech.solalat.user.data.daos.remote.horses.HorsesRemoteDao
import com.raantech.solalat.user.data.daos.remote.media.MediaRemoteDao
import com.raantech.solalat.user.data.daos.remote.medical.MedicalRemoteDao
import com.raantech.solalat.user.data.daos.remote.truck.TruckRemoteDao
import com.raantech.solalat.user.data.daos.remote.user.UserRemoteDao
import com.raantech.solalat.user.data.daos.remote.wishlist.WishListRemoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
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

    @Singleton
    @Provides
    fun provideMediaRemoteDao(
        retrofit: Retrofit
    ): MediaRemoteDao {
        return retrofit.create(MediaRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideBarnRemoteDao(
        retrofit: Retrofit
    ): BarnRemoteDao {
        return retrofit.create(BarnRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideTruckRemoteDao(
        retrofit: Retrofit
    ): TruckRemoteDao {
        return retrofit.create(TruckRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideHorseRemoteDao(
        retrofit: Retrofit
    ): HorsesRemoteDao {
        return retrofit.create(HorsesRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideWishListRemoteDao(
        retrofit: Retrofit
    ): WishListRemoteDao {
        return retrofit.create(WishListRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideMedicalRemoteDao(
        retrofit: Retrofit
    ): MedicalRemoteDao {
        return retrofit.create(MedicalRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideAccessoriesRemoteDao(
        retrofit: Retrofit
    ): AccessoriesRemoteDao {
        return retrofit.create(AccessoriesRemoteDao::class.java)
    }

    @Singleton
    @Provides
    fun provideFilterRemoteDao(
        retrofit: Retrofit
    ): FilterRemoteDao {
        return retrofit.create(FilterRemoteDao::class.java)
    }

}