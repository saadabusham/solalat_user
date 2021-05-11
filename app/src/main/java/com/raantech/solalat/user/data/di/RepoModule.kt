package com.raantech.solalat.user.data.di

import com.raantech.solalat.user.data.repos.accessories.AccessoriesRepo
import com.raantech.solalat.user.data.repos.accessories.AccessoriesRepoImp
import com.raantech.solalat.user.data.repos.barn.BarnRepo
import com.raantech.solalat.user.data.repos.barn.BarnRepoImp
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepoImp
import com.raantech.solalat.user.data.repos.horse.HorseRepo
import com.raantech.solalat.user.data.repos.horse.HorseRepoImp
import com.raantech.solalat.user.data.repos.media.MediaRepo
import com.raantech.solalat.user.data.repos.media.MediaRepoImp
import com.raantech.solalat.user.data.repos.medical.MedicalRepo
import com.raantech.solalat.user.data.repos.medical.MedicalRepoImp
import com.raantech.solalat.user.data.repos.truck.TruckRepo
import com.raantech.solalat.user.data.repos.truck.TruckRepoImp
import com.raantech.solalat.user.data.repos.user.UserRepo
import com.raantech.solalat.user.data.repos.user.UserRepoImp
import com.raantech.solalat.user.data.repos.wishlist.WishListRepo
import com.raantech.solalat.user.data.repos.wishlist.WishListRepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun bindConfigurationRepo(configurationRepoImp: ConfigurationRepoImp): ConfigurationRepo

    @Singleton
    @Binds
    abstract fun bindUserRepo(userRepoImp: UserRepoImp): UserRepo

    @Singleton
    @Binds
    abstract fun bindMediaRepo(mediaRepoImp: MediaRepoImp): MediaRepo

    @Singleton
    @Binds
    abstract fun bindBarnRepo(barnRepoImp: BarnRepoImp): BarnRepo

    @Singleton
    @Binds
    abstract fun bindTruckRepo(truckRepoImp: TruckRepoImp): TruckRepo

    @Singleton
    @Binds
    abstract fun bindHorseRepo(horseRepoImp: HorseRepoImp): HorseRepo

    @Singleton
    @Binds
    abstract fun bindWishListRepo(wishListRepoImp: WishListRepoImp): WishListRepo

    @Singleton
    @Binds
    abstract fun bindMedicalRepo(medicalRepoImp: MedicalRepoImp): MedicalRepo

    @Singleton
    @Binds
    abstract fun bindAccessoryRepo(accessoriesRepoImp: AccessoriesRepoImp): AccessoriesRepo

}