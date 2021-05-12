package com.raantech.solalat.user.data.repos.cart

import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.daos.local.cart.CartLocalDao
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class CartRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val cartLocalDao: CartLocalDao
) : BaseRepo(responseHandler), CartRepo {

    override suspend fun saveCart(accessory: Accessory) {
        cartLocalDao.saveCart(accessory)
    }

    override suspend fun saveCarts(accessories: List<Accessory>) {
        cartLocalDao.saveCarts(accessories)
    }

    override suspend fun loadCarts(): List<Accessory> {
        return cartLocalDao.getCarts()
    }

    override suspend fun getCart(id: Int): Accessory {
        return cartLocalDao.getCarts(id)
    }

    override suspend fun getCartsCount(): Int {
        return cartLocalDao.getCartsCount()
    }

    override suspend fun deleteCart(id: Int) {
        cartLocalDao.deleteCart(id)
    }

    override suspend fun clearCart() {
        cartLocalDao.clearCart()
    }


}