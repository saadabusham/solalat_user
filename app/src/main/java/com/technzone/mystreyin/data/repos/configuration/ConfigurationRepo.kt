package com.technzone.mystreyin.data.repos.configuration

import com.technzone.mystreyin.common.CommonEnums
import com.technzone.mystreyin.data.api.response.ResponseWrapper
import com.technzone.mystreyin.data.models.configuration.ConfigurationWrapperResponse
import io.reactivex.Single

interface ConfigurationRepo {

    fun setAppLanguage(selectedLanguage: CommonEnums.Languages)
    fun getAppLanguage(): CommonEnums.Languages

    suspend fun loadConfigurationData(): ResponseWrapper<ConfigurationWrapperResponse>
}