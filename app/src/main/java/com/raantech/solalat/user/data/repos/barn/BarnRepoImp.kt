package com.raantech.solalat.user.data.repos.barn

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.barn.BarnRemoteDao
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class BarnRepoImp @Inject constructor(
        responseHandler: ResponseHandler,
        private val barnRemoteDao: BarnRemoteDao
) : BaseRepo(responseHandler), BarnRepo {

    override suspend fun getBarns(
            skip: Int,
            latitude: Double?,
            longitude: Double?): APIResource<ResponseWrapper<List<Barn>>> {
        return try {
            responseHandle.handleSuccess(barnRemoteDao.getBarns(skip, latitude, longitude))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}