package com.raantech.solalat.user.data.daos.remote.horses

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.horses.AddHorseRequest
import com.raantech.solalat.user.data.models.horses.Horse
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
    ): ResponseWrapper<Horse>

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

}