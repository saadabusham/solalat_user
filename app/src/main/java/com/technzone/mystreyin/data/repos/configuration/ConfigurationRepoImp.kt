package com.technzone.mystreyin.data.repos.configuration

import com.technzone.mystreyin.common.CommonEnums
import com.technzone.mystreyin.data.api.response.ResponseWrapper
import com.technzone.mystreyin.data.daos.remote.configuration.ConfigurationRemoteDao
import com.technzone.mystreyin.data.models.configuration.ConfigurationWrapperResponse
import com.technzone.mystreyin.data.pref.configuration.ConfigurationPref
import com.technzone.mystreyin.data.repos.base.BaseRepo
import io.reactivex.Single
import javax.inject.Inject

class ConfigurationRepoImp @Inject constructor(
    private val configurationRemoteDao: ConfigurationRemoteDao,
    private val configurationPref: ConfigurationPref
) : BaseRepo(), ConfigurationRepo {

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