package com.raantech.solalat.user.ui.purchase

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil

class PurchasesViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val configurationRepo: ConfigurationRepo,
        private val sharedPreferencesUtil: SharedPreferencesUtil
) : BaseViewModel() {

    fun getFAQs() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.getFaqs(skip = 1000000, type = "user")
        emit(response)
    }

    fun getPhoneNumber(): String? {
        return sharedPreferencesUtil.getConfigurationPreferences().appPhoneNumber
    }

}