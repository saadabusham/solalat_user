package com.raantech.solalat.user.data.daos.remote.user

import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.NetworkConstants
import com.raantech.solalat.user.data.models.auth.login.TokenModel
import com.raantech.solalat.user.data.models.auth.login.UserDetailsResponseModel
import retrofit2.http.*

interface UserRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @FormUrlEncoded
    @POST("user/auth/login")
    suspend fun login(
            @Field("phone_number") phoneNumber: String
    ): ResponseWrapper<TokenModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @FormUrlEncoded
    @POST("user/auth/logout")
    suspend fun logout(
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @FormUrlEncoded
    @POST("user/auth/resend")
    suspend fun resendCode(
            @Field("token") token: String
    ): ResponseWrapper<TokenModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @FormUrlEncoded
    @POST("user/auth/verify")
    suspend fun verify(
            @Field("token") token: String,
            @Field("code") code: Int,
            @Field("device_token") device_token: String,
            @Field("platform") platform: String
    ): ResponseWrapper<UserDetailsResponseModel>

}