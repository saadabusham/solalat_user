package com.technzone.mystreyin.data.daos.remote.configuration

import com.technzone.mystreyin.data.api.response.ResponseWrapper
import com.technzone.mystreyin.data.models.configuration.ConfigurationWrapperResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ConfigurationRemoteDao {

    @GET("api/configuration")
    suspend fun getAppConfiguration(): ResponseWrapper<ConfigurationWrapperResponse>

}