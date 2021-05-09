package com.raantech.solalat.user.data.repos.wishlist

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.WishList
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.Query

interface WishListRepo {

    suspend fun getWishList(
            @Query("skip") skip: Int
    ): APIResource<ResponseWrapper<List<WishList>>>

    suspend fun addToWishList(
            @Field("product_id") productId: Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun removeFromWishList(
            @Path("product_id") productId: Int
    ): APIResource<ResponseWrapper<Any>>

}