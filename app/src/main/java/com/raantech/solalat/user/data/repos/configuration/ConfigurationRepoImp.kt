package com.raantech.solalat.user.data.repos.configuration

import com.raantech.solalat.user.common.CommonEnums
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.configuration.ConfigurationRemoteDao
import com.raantech.solalat.user.data.models.City
import com.raantech.solalat.user.data.models.ServicesItem
import com.raantech.solalat.user.data.models.configuration.ConfigurationWrapperResponse
import com.raantech.solalat.user.data.models.more.AboutUsResponse
import com.raantech.solalat.user.data.models.more.FaqsResponse
import com.raantech.solalat.user.data.models.ServiceCategoriesResponse
import com.raantech.solalat.user.data.pref.configuration.ConfigurationPref
import com.raantech.solalat.user.data.repos.base.BaseRepo
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

    override suspend fun getAboutUs(): APIResource<ResponseWrapper<AboutUsResponse>> {
        return try {
            responseHandle.handleSuccess(configurationRemoteDao.getAboutUs())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getFaqs(skip: Int, type: String): APIResource<ResponseWrapper<List<FaqsResponse>>> {
        return try {
            responseHandle.handleSuccess(configurationRemoteDao.getFaqs(type))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getCities(): APIResource<ResponseWrapper<List<City>>> {
        return try {
            responseHandle.handleSuccess(configurationRemoteDao.getCities())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getBarnServices(): APIResource<ResponseWrapper<List<ServicesItem>>> {
        return try {
            responseHandle.handleSuccess(configurationRemoteDao.getBarnServices())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }


    override suspend fun getServiceCategories(type: String, withCount: Boolean): APIResource<ResponseWrapper<ServiceCategoriesResponse>> {
        return try {
            responseHandle.handleSuccess(configurationRemoteDao.getServiceCategories(type, withCount))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getServiceCategories(type: String): APIResource<ResponseWrapper<ServiceCategoriesResponse>> {
        return try {
            responseHandle.handleSuccess(configurationRemoteDao.getServiceCategories(type))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}