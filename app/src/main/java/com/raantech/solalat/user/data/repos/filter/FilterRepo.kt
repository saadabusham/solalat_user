package com.raantech.solalat.user.data.repos.filter

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.data.models.wishlist.WishList

interface FilterRepo {

    suspend fun filterAccessories(
        type: String,
        searchText: String?,
        category_id: Int?,
        min_price: Double?,
        max_price: Double?
    ): APIResource<ResponseWrapper<List<Accessory>>>

    suspend fun filterHorses(
        type: String,
        searchText: String?,
        category_id: Int?,
        min_price: Double?,
        max_price: Double?,
        type_of_sale: String?
    ): APIResource<ResponseWrapper<List<Horse>>>

    suspend fun filterMedical(
        type: String,
        searchText: String?,
        category_id: Int?
    ): APIResource<ResponseWrapper<List<Medical>>>

    suspend fun filterTruck(
        type: String,
        searchText: String?
    ): APIResource<ResponseWrapper<List<Truck>>>

    suspend fun filterStable(
        type: String,
        searchText: String?,
        min_price: Double?,
        max_price: Double?
    ): APIResource<ResponseWrapper<List<Barn>>>
}