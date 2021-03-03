package com.technzone.base.data.repos.configuration

import com.technzone.base.common.CommonEnums
import com.technzone.base.data.api.response.ResponseHandler
import com.technzone.base.data.api.response.ResponseWrapper
import com.technzone.base.data.daos.remote.configuration.ConfigurationRemoteDao
import com.technzone.base.data.models.configuration.ConfigurationWrapperResponse
import com.technzone.base.data.pref.configuration.ConfigurationPref
import com.technzone.base.data.repos.base.BaseRepo
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
            ResponseWrapper<ConfigurationWrapperResponse> {
        return configurationRemoteDao.getAppConfiguration()
    }

}