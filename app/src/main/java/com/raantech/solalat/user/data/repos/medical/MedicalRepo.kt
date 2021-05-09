package com.raantech.solalat.user.data.repos.medical

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.medical.Medical
import retrofit2.http.Query

interface MedicalRepo {
    suspend fun getMedicals(
            skip: Int,
            category_id: Int?,
            latitude: Double?,
            longitude: Double?
    ): APIResource<ResponseWrapper<List<Medical>>>
}