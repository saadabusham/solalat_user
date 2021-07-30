package com.raantech.solalat.user.data.repos.filter

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseHandler
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.daos.remote.filter.FilterRemoteDao
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.data.models.wishlist.WishList
import com.raantech.solalat.user.data.repos.base.BaseRepo
import javax.inject.Inject

class FilterRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val filterRemoteDao: FilterRemoteDao
) : BaseRepo(responseHandler), FilterRepo {

    override suspend fun filterAccessories(
        type: String,
        searchText: String?,
        category_id: Int?,
        min_price: Double?,
        max_price: Double?
    ): APIResource<ResponseWrapper<List<Accessory>>> {
        return try {
            responseHandle.handleSuccess(
                filterRemoteDao.filterAccessories(
                    type,
                    searchText,
                    category_id,
                    min_price,
                    max_price
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun filterHorses(
        type: String,
        searchText: String?,
        category_id: Int?,
        min_price: Double?,
        max_price: Double?,
        type_of_sale: String?
    ): APIResource<ResponseWrapper<List<Horse>>> {
        return try {
            responseHandle.handleSuccess(
                filterRemoteDao.filterHorses(
                    type,
                    searchText,
                    category_id,
                    min_price,
                    max_price,
                    type_of_sale
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun filterMedical(
        type: String,
        searchText: String?,
        category_id: Int?
    ): APIResource<ResponseWrapper<List<Medical>>> {
        return try {
            responseHandle.handleSuccess(
                filterRemoteDao.filterMedical(
                    type,
                    searchText,
                    category_id
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun filterTruck(
        type: String,
        searchText: String?
    ): APIResource<ResponseWrapper<List<Truck>>> {
        return try {
            responseHandle.handleSuccess(
                filterRemoteDao.filterTruck(
                    type,
                    searchText
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun filterStable(
        type: String,
        searchText: String?,
        min_price: Double?,
        max_price: Double?
    ): APIResource<ResponseWrapper<List<Barn>>> {
        return try {
            responseHandle.handleSuccess(
                filterRemoteDao.filterStable(
                    type,
                    searchText,
                    min_price,
                    max_price
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}