package com.raantech.solalat.user.data.repos.truck

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.barn.BarnRemoteDao
import com.raantech.solalat.user.data.daos.remote.truck.TruckRemoteDao
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class TruckRepoImp @Inject constructor(
        responseHandler: ResponseHandler,
        private val truckRemoteDao: TruckRemoteDao
) : BaseRepo(responseHandler), TruckRepo {

    override suspend fun getTrucks(
        skip: Int,
        fromCity: Int?,
        toCity: Int?,
        latitude: Double?,
        longitude: Double?
    ): APIResource<ResponseWrapper<List<Truck>>> {
        return try {
            responseHandle.handleSuccess(truckRemoteDao.getTrucks(skip, fromCity, toCity, latitude, longitude))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}