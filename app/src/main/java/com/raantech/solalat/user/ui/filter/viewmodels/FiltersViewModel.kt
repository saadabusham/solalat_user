package com.raantech.solalat.user.ui.filter.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.enums.ServiceTypesEnum
import com.raantech.solalat.user.data.models.GeneralLookup
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.filter.FilterRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel

class FiltersViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val configurationRepo: ConfigurationRepo,
    private val filterRepo: FilterRepo
) : BaseViewModel() {

    val searchText: MutableLiveData<String> = MutableLiveData("")
    var selectedCategory: GeneralLookup? = null
    var selectedSubCategory: GeneralLookup? = null
    var minPrice: Double? = null
    var maxPrice: Double? = null
    var typeOfSale: String? = null
    val searchResults: MutableLiveData<String> = MutableLiveData("0")

    val horsesResults = mutableListOf<Horse>()
    val accessoriesResults = mutableListOf<Accessory>()
    val medicalResults = mutableListOf<Medical>()
    val stableResults = mutableListOf<Barn>()
    val truckResults = mutableListOf<Truck>()


    fun getServicesCategories(serviceTypesEnum: String) = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.getServiceCategories(serviceTypesEnum)
        emit(response)
    }

    fun filterAccessories() = liveData {
        emit(APIResource.loading())
        val response = filterRepo.filterAccessories(
            ServiceTypesEnum.ACCESSORIES.value,
            searchText.value,
            selectedSubCategory?.id,
            minPrice,
            maxPrice
        )
        emit(response)
    }

    fun filterHorses() = liveData {
        emit(APIResource.loading())
        val response = filterRepo.filterHorses(
            ServiceTypesEnum.HORSES.value,
            searchText.value,
            selectedSubCategory?.id,
            minPrice,
            maxPrice,
            typeOfSale
        )
        emit(response)
    }

    fun filterMedical() = liveData {
        emit(APIResource.loading())
        val response = filterRepo.filterMedical(
            ServiceTypesEnum.MEDICAL.value,
            searchText.value,
            selectedSubCategory?.id
        )
        emit(response)
    }

    fun filterTruck() = liveData {
        emit(APIResource.loading())
        val response = filterRepo.filterTruck(
            ServiceTypesEnum.TRANSPORTATION.value,
            searchText.value
        )
        emit(response)
    }

    fun filterStable() = liveData {
        emit(APIResource.loading())
        val response = filterRepo.filterStable(
            ServiceTypesEnum.BARN.value,
            searchText.value,
            minPrice,
            maxPrice
        )
        emit(response)
    }

}