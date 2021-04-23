package com.saad.base.data.repos.configuration

import com.saad.base.common.CommonEnums
import com.saad.base.data.api.response.APIResource
import com.saad.base.data.api.response.ResponseHandler
import com.saad.base.data.api.response.ResponseWrapper
import com.saad.base.data.daos.remote.configuration.ConfigurationRemoteDao
import com.saad.base.data.models.configuration.ConfigurationWrapperResponse
import com.saad.base.data.pref.configuration.ConfigurationPref
import com.saad.base.data.repos.base.BaseRepo
import javax.inject.Inject

class ConfigurationRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val configurationRemoteDao: ConfigurationRemoteDao,
    private val configurationPref: ConfigurationPref
) : BaseRepo(responseHandler), ConfigurationRepo {

    override fun setAppLanguage(selectedLanguage: CommonEnums.Languages) {
        configurationPref.setAppLanguageValue(selectedLanguage.value)
    }

    override fun getAppLanguage(): CommonEnums.Languages {
        return CommonEnums.Languages.getLanguageByValue(configurationPref.getAppLanguageValue())
    }

    override suspend fun loadConfigurationData():
            APIResource<ResponseWrapper<ConfigurationWrapperResponse>> {
        return try {
            responseHandle.handleSuccess(configurationRemoteDao.getAppConfiguration())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}