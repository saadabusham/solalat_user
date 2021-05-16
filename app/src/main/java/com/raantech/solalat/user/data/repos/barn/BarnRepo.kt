package com.raantech.solalat.user.data.repos.barn

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.barn.Barn
import retrofit2.http.Path
import retrofit2.http.Query

interface BarnRepo {
    suspend fun getBarns(
            skip: Int,
            latitude: Double?,
            longitude: Double?
    ): APIResource<ResponseWrapper<List<Barn>>>

    suspend fun getBarn(
        id: Int
    ): APIResource<ResponseWrapper<Barn>>

}