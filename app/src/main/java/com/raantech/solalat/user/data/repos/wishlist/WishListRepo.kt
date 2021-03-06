package com.raantech.solalat.user.data.repos.wishlist

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.wishlist.WishList
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.Query

interface WishListRepo {

    suspend fun getWishList(
        skip: Int
    ): APIResource<ResponseWrapper<List<WishList>>>

    suspend fun addToWishList(
        entity_type:String,
        entity_id:Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun removeFromWishList(
        productId: Int,
        entity_type: String
    ): APIResource<ResponseWrapper<Any>>

}