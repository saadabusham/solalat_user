package com.raantech.solalat.user.data.daos.remote.wishlist

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.WishList
import com.raantech.solalat.user.data.models.auth.login.TokenModel
import retrofit2.http.*

interface WishListRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("wishlist")
    suspend fun getWishList(
            @Query("skip") skip: Int
    ): ResponseWrapper<List<WishList>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @POST("wishlist/store")
    suspend fun addToWishList(
            @Field("product_id") productId:Int
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @DELETE("wishlist/{product_id}/destroy")
    suspend fun removeFromWishList(
            @Path("product_id") productId:Int
    ): ResponseWrapper<Any>

}