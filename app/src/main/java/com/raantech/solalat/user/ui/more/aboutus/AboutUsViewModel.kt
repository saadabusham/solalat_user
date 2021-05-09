package com.raantech.solalat.user.ui.more.aboutus

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.models.configuration.ConfigurationWrapperResponse
import com.raantech.solalat.user.data.pref.configuration.ConfigurationPref
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil

class AboutUsViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        val sharedPreferencesUtil: SharedPreferencesUtil,
        val configurationPref: ConfigurationPref,
        private val configurationRepo: ConfigurationRepo
) : BaseViewModel() {

    fun getAboutUs() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.getAboutUs()
        emit(response)
    }


    fun getSocialMedia(): ConfigurationWrapperResponse? {
        return sharedPreferencesUtil.getConfigurationPreferences()
    }

}