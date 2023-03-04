package com.raantech.solalat.user.ui.splash

import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.enums.UserEnums
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.user.UserRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val configurationRepo: ConfigurationRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil
) : BaseViewModel() {

    fun getConfigurationData() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.loadConfigurationData()
        emit(response)
    }

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
}