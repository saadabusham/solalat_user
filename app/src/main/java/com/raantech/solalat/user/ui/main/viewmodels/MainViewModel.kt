package com.raantech.solalat.user.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.raantech.solalat.user.common.CommonEnums
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.enums.HorseAdsTypeEnum
import com.raantech.solalat.user.data.enums.UserEnums
import com.raantech.solalat.user.data.enums.WishListType
import com.raantech.solalat.user.data.models.City
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.map.Address
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.data.pref.configuration.ConfigurationPref
import com.raantech.solalat.user.data.pref.user.UserPref
import com.raantech.solalat.user.data.repos.accessories.AccessoriesRepo
import com.raantech.solalat.user.data.repos.barn.BarnRepo
import com.raantech.solalat.user.data.repos.cart.CartRepo
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.horse.HorseRepo
import com.raantech.solalat.user.data.repos.medical.MedicalRepo
import com.raantech.solalat.user.data.repos.truck.TruckRepo
import com.raantech.solalat.user.data.repos.user.UserRepo
import com.raantech.solalat.user.data.repos.wishlist.WishListRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val userPref: UserPref,
    private val configurationPref: ConfigurationPref,
    private val configurationRepo: ConfigurationRepo,
    private val barnRepo: BarnRepo,
    private val truckRepo: TruckRepo,
    private val horseRepo: HorseRepo,
    private val wishListRepo: WishListRepo,
    private val medicalRepo: MedicalRepo,
    private val accessoriesRepo: AccessoriesRepo,
    private val cartRepo: CartRepo
) : BaseViewModel() {
    var address: Address? = null

    //    var cities: MutableList<City> = mutableListOf()
    var fromCity: City? = null
    var toCity: City? = null
    val horseAdsTypeEnum: MutableLiveData<HorseAdsTypeEnum> = MutableLiveData(HorseAdsTypeEnum.ALL)
    val barn: MutableLiveData<Barn> = MutableLiveData()
    val truck: MutableLiveData<Truck> = MutableLiveData()
    val accessory: MutableLiveData<Accessory> = MutableLiveData()
    var category: ServiceCategory? = null

    val cartCount: MutableLiveData<String> = MutableLiveData("0")

    fun getCartsCount() = viewModelScope.launch {
        cartRepo.getCartsCount().observeForever {
            if (it != null)
                cartCount.postValue(it.toString())
            else
                cartCount.postValue("0")
        }
    }

    fun logoutRemote() = liveData {
        emit(APIResource.loading())
        val response = userRepo.logout()
        emit(response)
    }

    fun logoutLocale() {
        if (userRepo.getTouchIdStatus())
            sharedPreferencesUtil.logout()
        else {
            sharedPreferencesUtil.clearPreference()
            userPref.setIsFirstOpen(false)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
    }

    fun saveLanguage() = liveData {
        configurationPref.setAppLanguageValue(if (configurationPref.getAppLanguageValue() == "ar") CommonEnums.Languages.English.value else CommonEnums.Languages.Arabic.value)
        emit(null)
    }

    fun getAppLanguage(): String {
        return configurationPref.getAppLanguageValue()
    }

    fun getServicesCategories(type: String) = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.getServiceCategories(type, true)
        emit(response)
    }

    fun getBarns(
        skip: Int,
        latitude: Double?,
        longitude: Double?
    ) = liveData {
        emit(APIResource.loading())
        val response = barnRepo.getBarns(skip, latitude, longitude)
        emit(response)
    }

    fun getBarnDetails(
        id: Int
    ) = liveData {
        emit(APIResource.loading())
        val response = barnRepo.getBarn(id)
        emit(response)
    }

    fun getCities() = liveData {
        emit(APIResource.loading())
        val response = configurationRepo.getCities()
        emit(response)
    }

    fun getTrucks(
        skip: Int,
        fromCity: Int?,
        toCity: Int?,
        latitude: Double?,
        longitude: Double?
    ) = liveData {
        emit(APIResource.loading())
        val response = truckRepo.getTrucks(skip, fromCity, toCity, latitude, longitude)
        emit(response)
    }

    fun getTruck(
        id: Int
    ) = liveData {
        emit(APIResource.loading())
        val response = truckRepo.getTruck(id)
        emit(response)
    }

    fun getHorses(
        skip: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            horseRepo.getHorses(
                horseAdsTypeEnum.value?.value
                    ?: HorseAdsTypeEnum.ALL.value, skip
            )
        emit(response)
    }

    fun addToWishList(
        productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            wishListRepo.addToWishList(WishListType.PRODUCT.value, productId)
        emit(response)
    }

    fun removeFromWishList(
        productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            wishListRepo.removeFromWishList(productId, WishListType.PRODUCT.value)
        emit(response)
    }

    fun getMedicals(
        skip: Int,
        categoryId: Int?,
        latitude: Double?,
        longitude: Double?
    ) = liveData {
        emit(APIResource.loading())
        val response = medicalRepo.getMedicals(skip, categoryId, latitude, longitude)
        emit(response)
    }


    fun getAccessories(
        skip: Int,
        categoryId: Int?
    ) = liveData {
        emit(APIResource.loading())
        val response = accessoriesRepo.getAccessories(skip, categoryId)
        emit(response)
    }

    fun addToCart(accessory: Accessory) = viewModelScope.launch {
        cartRepo.saveCart(accessory)
    }

    fun getCarts() = liveData {
        val response = cartRepo.loadCarts()
        emit(response)
    }

    fun getCart(id: Int) = liveData {
        val response = cartRepo.getCart(id)
        emit(response)
    }

    fun getAccessory(
        id: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            accessoriesRepo.getAccessory(id)
        emit(response)
    }


}