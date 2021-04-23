package com.saad.base.data.repos.auth

import com.saad.base.data.api.response.APIResource
import com.saad.base.data.api.response.ResponseWrapper
import com.saad.base.data.enums.UserEnums
import com.saad.base.data.models.auth.login.UserDetailsResponseModel


interface UserRepo {


    suspend fun login(
        userName: String,
        password: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    fun saveNotificationStatus(flag: Boolean)
    fun getNotificationStatus(): Boolean

    fun saveTouchIdStatus(flag: Boolean)
    fun getTouchIdStatus(): Boolean

    fun saveAccessToken(accessToken: String)
    fun getAccessToken(): String

    fun saveUserPassword(password: String)
    fun getUserPassword(): String

    fun setUserStatus(userState: UserEnums.UserState)
    fun getUserStatus(): UserEnums.UserState

    fun setUser(user: UserDetailsResponseModel)
    fun getUser(): UserDetailsResponseModel?
}