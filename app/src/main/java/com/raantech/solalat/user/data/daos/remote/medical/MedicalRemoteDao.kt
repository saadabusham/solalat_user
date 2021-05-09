package com.raantech.solalat.user.data.daos.remote.medical

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.medical.Medical
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MedicalRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("medicals")
    suspend fun getMedicals(
            @Query("skip") skip: Int,
            @Query("category_id") category_id: Int?,
            @Query("latitude") latitude: Double?,
            @Query("longitude") longitude: Double?
    ): ResponseWrapper<List<Medical>>

}