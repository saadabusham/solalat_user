package com.raantech.solalat.user.ui.updateprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.enums.UserEnums
import com.raantech.solalat.user.data.models.map.Address
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.user.UserRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val configurationRepo: ConfigurationRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil
) : BaseViewModel() {

    val name: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val addressString: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: MutableLiveData<String> = MutableLiveData()
    val selectedCountryCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val address: MutableLiveData<Address> = MutableLiveData()

    fun getConfigurationData() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.loadConfigurationData()
        emit(response)
    }

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
}