package com.raantech.solalat.user.data.repos.horse

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.horses.Horse

interface HorseRepo {

    suspend fun getHorses(
        type_of_sale : String,
        skip: Int?
    ): APIResource<ResponseWrapper<List<Horse>>>
}