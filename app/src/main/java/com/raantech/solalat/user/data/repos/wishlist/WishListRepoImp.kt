package com.raantech.solalat.user.data.repos.wishlist

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.wishlist.WishListRemoteDao
import com.raantech.solalat.user.data.models.WishList
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class WishListRepoImp @Inject constructor(
        responseHandler: ResponseHandler,
        private val wishListRemoteDao: WishListRemoteDao
) : BaseRepo(responseHandler), WishListRepo {

    override suspend fun getWishList(skip: Int): APIResource<ResponseWrapper<List<WishList>>> {
        return try {
            responseHandle.handleSuccess(wishListRemoteDao.getWishList(skip))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun addToWishList(productId: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(wishListRemoteDao.addToWishList(productId))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun removeFromWishList(productId: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(wishListRemoteDao.removeFromWishList(productId))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}