package com.raantech.solalat.user.data.repos.cart

import androidx.lifecycle.LiveData
import com.raantech.solalat.user.data.models.accessories.Accessory

interface CartRepo {

    suspend fun saveCart(accessory: Accessory)
    suspend fun saveCarts(accessories: List<Accessory>)
    suspend fun loadCarts(): List<Accessory>
    suspend fun getCart(id: Int): Accessory
    fun getCartsCount(): LiveData<Int>
    suspend fun deleteCart(id:Int)
    suspend fun clearCart()
}