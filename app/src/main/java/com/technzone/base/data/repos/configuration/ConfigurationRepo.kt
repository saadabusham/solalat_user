package com.technzone.base.data.repos.configuration

import com.technzone.base.common.CommonEnums
import com.technzone.base.data.api.response.APIResource
import com.technzone.base.data.api.response.ResponseWrapper
import com.technzone.base.data.models.configuration.ConfigurationWrapperResponse

interface ConfigurationRepo {

    fun setAppLanguage(selectedLanguage: CommonEnums.Languages)
    fun getAppLanguage(): CommonEnums.Languages

    suspend fun loadConfigurationData(): APIResource<ResponseWrapper<ConfigurationWrapperResponse>>
}