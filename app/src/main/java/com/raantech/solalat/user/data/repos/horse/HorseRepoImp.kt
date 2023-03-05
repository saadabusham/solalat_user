package com.raantech.solalat.user.data.repos.horse

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.horses.HorsesRemoteDao
import com.raantech.solalat.user.data.models.horses.AddHorseRequest
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.horses.HorseDetails
import com.raantech.solalat.user.data.models.horses.horsesubscription.HorseSubscription
import com.raantech.solalat.user.data.models.horses.horsesubscription.IncreaseResponse
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class HorseRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val horsesRemoteDao: HorsesRemoteDao
) : BaseRepo(responseHandler), HorseRepo {

    override suspend fun getHorses(
        type_of_sale: String,
        skip: Int?
    ): APIResource<ResponseWrapper<List<Horse>>> {
        return try {
            responseHandle.handleSuccess(horsesRemoteDao.getHorses(type_of_sale, skip))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getHorse(id: Int): APIResource<ResponseWrapper<HorseDetails>> {
        return try {
            responseHandle.handleSuccess(horsesRemoteDao.getHorse(id))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun addHorses(addHorseRequest: AddHorseRequest): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(horsesRemoteDao.addHorses(addHorseRequest))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun updateHorses(
        id: Int,
        addHorseRequest: AddHorseRequest
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(horsesRemoteDao.updateHorse(id, addHorseRequest))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun addAuctionSubscription(
        horseId: Int,
        paymentMethod: String
    ): APIResource<ResponseWrapper<HorseSubscription>> {
        return try {
            responseHandle.handleSuccess(
                horsesRemoteDao.addAuctionSubscription(
                    horseId,
                    paymentMethod
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun cancelAuctionSubscription(horseId: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(horsesRemoteDao.cancelAuctionSubscription(horseId))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun increaseAuctionSubscription(horseId: Int): APIResource<ResponseWrapper<IncreaseResponse>> {
        return try {
            responseHandle.handleSuccess(horsesRemoteDao.increaseAuctionSubscription(horseId))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}