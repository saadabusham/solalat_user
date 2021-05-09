package com.raantech.solalat.user.data.repos.configuration

import com.raantech.solalat.user.data.models.more.AboutUsResponse
import com.raantech.solalat.user.data.models.more.FaqsResponse
import com.raantech.solalat.user.data.models.ServiceCategoriesResponse
import com.raantech.solalat.user.common.CommonEnums
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.City
import com.raantech.solalat.user.data.models.ServicesItem
import com.raantech.solalat.user.data.models.configuration.ConfigurationWrapperResponse

interface ConfigurationRepo {

    fun setAppLanguage(selectedLanguage: CommonEnums.Languages)
    fun getAppLanguage(): CommonEnums.Languages

    suspend fun loadConfigurationData(): APIResource<ResponseWrapper<ConfigurationWrapperResponse>>
    suspend fun getAboutUs(): APIResource<ResponseWrapper<AboutUsResponse>>
    suspend fun getServiceCategories(
            type: String,withCount:Boolean
    ): APIResource<ResponseWrapper<ServiceCategoriesResponse>>

    suspend fun getFaqs(
            skip: Int,
            type: String
    ): APIResource<ResponseWrapper<List<FaqsResponse>>>

    suspend fun getCities(
    ): APIResource<ResponseWrapper<List<City>>>

    suspend fun getBarnServices(
    ): APIResource<ResponseWrapper<List<ServicesItem>>>

}