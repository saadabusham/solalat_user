package com.raantech.solalat.user.data.repos.accessories

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.accessories.AccessoriesRemoteDao
import com.raantech.solalat.user.data.daos.remote.barn.BarnRemoteDao
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class AccessoriesRepoImp @Inject constructor(
        responseHandler: ResponseHandler,
        private val accessoriesRemoteDao: AccessoriesRemoteDao
) : BaseRepo(responseHandler), AccessoriesRepo {

    override suspend fun getAccessories(
        skip: Int,
        category_id: Int?
    ): APIResource<ResponseWrapper<List<Accessory>>> {
        return try {
            responseHandle.handleSuccess(accessoriesRemoteDao.getAccessories(skip, category_id))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}