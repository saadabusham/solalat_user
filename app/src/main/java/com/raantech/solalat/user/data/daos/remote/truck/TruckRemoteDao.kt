package com.raantech.solalat.user.data.daos.remote.truck

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.truck.Truck
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TruckRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("trucks")
    suspend fun getTrucks(
            @Query("skip") skip: Int,
            @Query("from_city") fromCity: Int?,
            @Query("to_city") toCity: Int?,
            @Query("latitude") latitude: Double?,
            @Query("longitude") longitude: Double?
    ): ResponseWrapper<List<Truck>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("trucks/{id}/show")
    suspend fun getTruck(
            @Path("id") id: Int
    ): ResponseWrapper<Truck>

}