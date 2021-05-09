package com.raantech.solalat.user.data.repos.medical

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.barn.BarnRemoteDao
import com.raantech.solalat.user.data.daos.remote.medical.MedicalRemoteDao
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class MedicalRepoImp @Inject constructor(
        responseHandler: ResponseHandler,
        private val medicalRemoteDao: MedicalRemoteDao
) : BaseRepo(responseHandler), MedicalRepo {

    override suspend fun getMedicals(skip: Int, category_id: Int?, latitude: Double?, longitude: Double?): APIResource<ResponseWrapper<List<Medical>>> {
        return try {
            responseHandle.handleSuccess(medicalRemoteDao.getMedicals(skip,category_id, latitude, longitude))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}