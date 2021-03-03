package com.technzone.base.data.di


import com.technzone.base.data.repos.auth.UserRepo
import com.technzone.base.data.repos.auth.UserRepoImp
import com.technzone.base.data.repos.configuration.ConfigurationRepo
import com.technzone.base.data.repos.configuration.ConfigurationRepoImp
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
    abstract fun bindUserRepo(userRepoImp: UserRepoImp) : UserRepo



}