package com.raantech.solalat.user.ui.more.faqs

import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqsViewModel @Inject constructor(
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