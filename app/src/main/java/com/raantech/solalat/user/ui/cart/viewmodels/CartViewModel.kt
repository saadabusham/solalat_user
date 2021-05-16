package com.raantech.solalat.user.ui.cart.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.raantech.solalat.user.R2
import com.raantech.solalat.user.data.models.Price
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.pref.user.UserPref
import com.raantech.solalat.user.data.repos.cart.CartRepo
import com.raantech.solalat.user.data.repos.user.UserRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import kotlinx.coroutines.launch

class CartViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val cartRepo: CartRepo
) : BaseViewModel() {
    companion object{
    }

    val TAX_CONST:Double = 0.15
    var tax:MutableLiveData<Price> = MutableLiveData()
    var subTotal:MutableLiveData<Price> = MutableLiveData()
    var total:MutableLiveData<Price> = MutableLiveData()
    fun updateCartItem(accessory: Accessory) = viewModelScope.launch {
        cartRepo.saveCart(accessory)
    }

    fun deleteCart(id: Int) = viewModelScope.launch {
        cartRepo.deleteCart(id)
    }

    fun getCarts() = liveData {
        val response = cartRepo.loadCarts()
        emit(response)
    }

    fun getCart(id: Int) = liveData {
        val response = cartRepo.getCart(id)
        emit(response)
    }
    fun getCartsCount() = liveData {
        val response = cartRepo.getCartsCount()
        emit(response)
    }


}