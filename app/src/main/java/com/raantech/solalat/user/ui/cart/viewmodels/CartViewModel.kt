package com.raantech.solalat.user.ui.cart.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
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
    var tax:Price = Price("10.0",formatted = "10.0 ر.س")
    var subTotal:Price = Price("10.0",formatted = "10.0 ر.س")
    var total:Price = Price("10.0",formatted = "10.0 ر.س")
    fun addToCart(accessory: Accessory) = viewModelScope.launch {
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