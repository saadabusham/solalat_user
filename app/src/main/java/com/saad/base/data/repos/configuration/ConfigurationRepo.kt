package com.saad.base.data.repos.configuration

import com.saad.base.common.CommonEnums
import com.saad.base.data.api.response.APIResource
import com.saad.base.data.api.response.ResponseWrapper
import com.saad.base.data.models.configuration.ConfigurationWrapperResponse

interface ConfigurationRepo {

    fun setAppLanguage(selectedLanguage: CommonEnums.Languages)
    fun getAppLanguage(): CommonEnums.Languages

    suspend fun loadConfigurationData(): APIResource<ResponseWrapper<ConfigurationWrapperResponse>>
}