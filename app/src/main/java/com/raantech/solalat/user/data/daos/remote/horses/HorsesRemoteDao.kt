package com.raantech.solalat.user.data.daos.remote.horses

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.horses.AddHorseRequest
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.horses.HorseDetails
import com.raantech.solalat.user.data.models.horses.horsesubscription.HorseSubscription
import retrofit2.http.*

interface HorsesRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("horses")
    suspend fun getHorses(
        @Query("type_of_sale") type_of_sale: String,
        @Query("skip") skip: Int?
    ): ResponseWrapper<List<Horse>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("horses/{id}")
    suspend fun getHorse(
        @Path("id") id: Int
    ): ResponseWrapper<HorseDetails>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("user/horses")
    suspend fun addHorses(
        @Body addHorseRequest: AddHorseRequest
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @PUT("user/horses/{id}")
    suspend fun updateHorse(
        @Path("id") id: Int,
        @Body addHorseRequest: AddHorseRequest
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("horses/auction/subscriptions/store")
    suspend fun addAuctionSubscription(
        @Query("horse_id") horseId: Int,
        @Query("payment_method") paymentMethod: String,
    ): ResponseWrapper<HorseSubscription>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("horses/auction/subscriptions/{horseId}/destory")
    suspend fun cancelAuctionSubscription(
        @Path("horseId") horseId: Int
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("horses/auction/subscriptions/{horseId}/minimum-bids")
    suspend fun increaseAuctionSubscription(
        @Path("horseId") horseId: Int
    ): ResponseWrapper<Any>

}