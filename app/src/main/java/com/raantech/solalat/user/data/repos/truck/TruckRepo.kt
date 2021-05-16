package com.raantech.solalat.user.data.repos.truck

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.truck.Truck
import retrofit2.http.Query

interface TruckRepo {

    suspend fun getTrucks(
        skip: Int,
        fromCity: Int?,
        toCity: Int?,
        latitude: Double?,
        longitude: Double?
    ): APIResource<ResponseWrapper<List<Truck>>>

    suspend fun getTruck(
        id: Int
    ): APIResource<ResponseWrapper<Truck>>

}