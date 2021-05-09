package com.raantech.solalat.user.data.daos.remote.configuration

import com.raantech.solalat.user.data.models.more.AboutUsResponse
import com.raantech.solalat.user.data.models.more.FaqsResponse
import com.raantech.solalat.user.data.models.ServiceCategoriesResponse
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.City
import com.raantech.solalat.user.data.models.ServicesItem
import com.raantech.solalat.user.data.models.configuration.ConfigurationWrapperResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ConfigurationRemoteDao {

    @GET("app/settings")
    suspend fun getAppConfiguration(): ResponseWrapper<ConfigurationWrapperResponse>


    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @GET("app/aboutUs")
    suspend fun getAboutUs(): ResponseWrapper<AboutUsResponse>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("faqs")
    suspend fun getFaqs(
            @Query("type") type: String
    ): ResponseWrapper<List<FaqsResponse>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("categories")
    suspend fun getServiceCategories(
            @Query("type") type: String,
            @Query("withCount") withCount: Boolean
    ): ResponseWrapper<ServiceCategoriesResponse>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("app/cities")
    suspend fun getCities(
    ): ResponseWrapper<List<City>>


    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("services/stables")
    suspend fun getBarnServices(
    ): ResponseWrapper<List<ServicesItem>>


}