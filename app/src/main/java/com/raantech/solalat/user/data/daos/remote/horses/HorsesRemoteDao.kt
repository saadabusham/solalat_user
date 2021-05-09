package com.raantech.solalat.user.data.daos.remote.horses

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.horses.Horse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HorsesRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("horses")
    suspend fun getHorses(
        @Query("type_of_sale") type_of_sale : String,
        @Query("skip") skip: Int?
    ): ResponseWrapper<List<Horse>>

}