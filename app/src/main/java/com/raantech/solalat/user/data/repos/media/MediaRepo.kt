package com.raantech.solalat.user.data.repos.media

import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.models.media.Media
import okhttp3.MultipartBody

interface MediaRepo {
    suspend fun getMedia(
            skip: Int
    ): APIResource<ResponseWrapper<List<Media>>>

    suspend fun uploadMedia(
        mediaFile : MultipartBody.Part
    ): APIResource<ResponseWrapper<Any>>

    suspend fun deleteMedia(
        mediaId: Int
    ): APIResource<ResponseWrapper<Any>>
}