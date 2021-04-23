package com.raantech.solalat.provider.data.daos.remote.configuration

import com.raantech.solalat.provider.data.api.response.ResponseWrapper
import com.raantech.solalat.provider.data.models.configuration.ConfigurationWrapperResponse
import retrofit2.http.GET

interface ConfigurationRemoteDao {

    @GET("api/configuration")
    suspend fun getAppConfiguration(): ResponseWrapper<ConfigurationWrapperResponse>
}