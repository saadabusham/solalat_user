package com.raantech.solalat.user.data.daos.remote.barn

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.barn.Barn
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BarnRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("stables")
    suspend fun getBarns(
            @Query("skip") skip: Int,
            @Query("latitude") latitude: Double?,
            @Query("longitude") longitude: Double?
    ): ResponseWrapper<List<Barn>>

}