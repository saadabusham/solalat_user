package com.raantech.solalat.user.data.repos.accessories

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.accessories.Accessory

interface AccessoriesRepo {

    suspend fun getAccessories(
        skip: Int,
        category_id: Int?
    ): APIResource<ResponseWrapper<List<Accessory>>>

    suspend fun getAccessory(
        id: Int
    ): APIResource<ResponseWrapper<Accessory>>
}