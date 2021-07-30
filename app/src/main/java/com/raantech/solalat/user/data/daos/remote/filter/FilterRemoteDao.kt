package com.raantech.solalat.user.data.daos.remote.filter

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.data.models.truck.Truck
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FilterRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("search")
    suspend fun filterAccessories(
        @Query("type") type: String,
        @Query("searchText") searchText: String?,
        @Query("category_id") category_id: Int?,
        @Query("min_price") min_price: Double?,
        @Query("max_price") max_price: Double?
    ): ResponseWrapper<List<Accessory>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("search")
    suspend fun filterHorses(
        @Query("type") type: String,
        @Query("searchText") searchText: String?,
        @Query("category_id") category_id: Int?,
        @Query("min_price") min_price: Double?,
        @Query("max_price") max_price: Double?,
        @Query("type_of_sale") type_of_sale: String?
    ): ResponseWrapper<List<Horse>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("search")
    suspend fun filterMedical(
        @Query("type") type: String,
        @Query("searchText") searchText: String?,
        @Query("category_id") category_id: Int?
    ): ResponseWrapper<List<Medical>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("search")
    suspend fun filterTruck(
        @Query("type") type: String,
        @Query("searchText") searchText: String?
    ): ResponseWrapper<List<Truck>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("search")
    suspend fun filterStable(
        @Query("type") type: String,
        @Query("searchText") searchText: String?,
        @Query("min_price") min_price: Double?,
        @Query("max_price") max_price: Double?
    ): ResponseWrapper<List<Barn>>
}