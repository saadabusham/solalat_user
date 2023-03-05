package com.raantech.solalat.user.data.repos.horse

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.horses.AddHorseRequest
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.horses.HorseDetails
import com.raantech.solalat.user.data.models.horses.horsesubscription.HorseSubscription
import com.raantech.solalat.user.data.models.horses.horsesubscription.IncreaseResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface HorseRepo {

    suspend fun getHorses(
        type_of_sale: String,
        skip: Int?
    ): APIResource<ResponseWrapper<List<Horse>>>

    suspend fun getHorse(
        id: Int
    ): APIResource<ResponseWrapper<HorseDetails>>

    suspend fun addHorses(
        addHorseRequest: AddHorseRequest
    ): APIResource<ResponseWrapper<Any>>

    suspend fun updateHorses(
        id: Int,
        addHorseRequest: AddHorseRequest
    ): APIResource<ResponseWrapper<Any>>

    suspend fun addAuctionSubscription(
        horseId: Int,
        paymentMethod: String,
    ): APIResource<ResponseWrapper<HorseSubscription>>

    suspend fun cancelAuctionSubscription(
        horseId: Int
    ): APIResource<ResponseWrapper<Any>>

    suspend fun increaseAuctionSubscription(
        horseId: Int
    ): APIResource<ResponseWrapper<IncreaseResponse>>
}