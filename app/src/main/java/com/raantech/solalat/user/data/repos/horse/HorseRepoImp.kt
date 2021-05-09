package com.raantech.solalat.user.data.repos.horse

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.horses.HorsesRemoteDao
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class HorseRepoImp @Inject constructor(
        responseHandler: ResponseHandler,
        private val horsesRemoteDao: HorsesRemoteDao
) : BaseRepo(responseHandler), HorseRepo {

    override suspend fun getHorses(type_of_sale: String, skip: Int?): APIResource<ResponseWrapper<List<Horse>>> {
        return try {
            responseHandle.handleSuccess(horsesRemoteDao.getHorses(type_of_sale, skip))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}