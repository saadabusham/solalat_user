package com.saad.base.data.daos.remote.configuration

import com.saad.base.data.api.response.ResponseWrapper
import com.saad.base.data.models.configuration.ConfigurationWrapperResponse
import retrofit2.http.GET

interface ConfigurationRemoteDao {

    @GET("api/configuration")
    suspend fun getAppConfiguration(): ResponseWrapper<ConfigurationWrapperResponse>
}