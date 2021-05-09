package com.raantech.solalat.user.data.repos.truck

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.truck.Truck
import retrofit2.http.Query

interface TruckRepo {

    suspend fun getTrucks(
        @Query("skip") skip: Int,
        @Query("from_city") fromCity: Int?,
        @Query("to_city") toCity: Int?,
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?
    ): APIResource<ResponseWrapper<List<Truck>>>

}