package com.raantech.solalat.user.ui.addhorse.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.enums.HorseAdsTypeEnum
import com.raantech.solalat.user.data.enums.ServiceTypesEnum
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.horses.AddHorseRequest
import com.raantech.solalat.user.data.models.horses.Files
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.horse.HorseRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.extensions.checkPhoneNumberFormat
import com.raantech.solalat.user.utils.extensions.concatStrings

class AddHorseViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val horseRepo: HorseRepo,
        private val configurationRepo: ConfigurationRepo
) : BaseViewModel() {

    val horseAdsTypeEnum: MutableLiveData<HorseAdsTypeEnum> = MutableLiveData(HorseAdsTypeEnum.SELL)
    var category: ServiceCategory? = null
    val horseName: MutableLiveData<String> = MutableLiveData()
    val fatherName: MutableLiveData<String> = MutableLiveData()
    val motherName: MutableLiveData<String> = MutableLiveData()
    val horsePrice: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: MutableLiveData<String> = MutableLiveData()
    val selectedCountryCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val files: MutableList<Int> = mutableListOf()
    var isPollinated: Boolean = false
    var gender: String = ""
    var safety: String = ""
    var age: Int = -1
    var height: String = ""

    fun addHorse(addHorseRequest: AddHorseRequest) = liveData {
        emit(APIResource.loading())
        val response = horseRepo.addHorses(addHorseRequest)
        emit(response)
    }

    fun getHorseRequest(receivedWhatsapp: Boolean, isActive: Boolean): AddHorseRequest {
        return AddHorseRequest(
                isVaccinated = isPollinated,
                isActive = isActive,
                sex = gender,
                motherName = motherName.value,
                contactNumber = phoneNumber.value.toString().checkPhoneNumberFormat()
                        .concatStrings(selectedCountryCode.value.toString()),
                typeOfSale = horseAdsTypeEnum.value?.value,
                categoryId = category?.id,
                fatherName = fatherName.value,
                price = horsePrice.value?.toDouble(),
                safety = safety,
                name = horseName.value,
                age = age,
                receivedWhatsapp = receivedWhatsapp,
                height = height,
                files = Files(baseImage = files[0], additionalImages = files)
        )
    }

    fun getServicesCategories() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.getServiceCategories(ServiceTypesEnum.HORSES.value)
        emit(response)
    }

}