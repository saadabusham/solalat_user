package com.raantech.solalat.user.ui.reportprovider

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.enums.ServiceTypesEnum
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.user.UserRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportProviderViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val configurationRepo: ConfigurationRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil
) : BaseViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val summery: MutableLiveData<String> = MutableLiveData()
    fun getConfigurationData() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.loadConfigurationData()
        emit(response)
    }


    fun getServicesCategories() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.getServiceCategories(ServiceTypesEnum.HORSES.value)
        emit(response)
    }
}