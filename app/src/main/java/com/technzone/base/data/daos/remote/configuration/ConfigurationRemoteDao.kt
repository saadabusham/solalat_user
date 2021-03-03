package com.technzone.base.data.daos.remote.configuration

import com.technzone.base.data.api.response.ResponseWrapper
import com.technzone.base.data.models.configuration.ConfigurationWrapperResponse
import retrofit2.http.GET

interface ConfigurationRemoteDao {

    @GET("api/configuration")
    suspend fun getAppConfiguration(): ResponseWrapper<ConfigurationWrapperResponse>
}