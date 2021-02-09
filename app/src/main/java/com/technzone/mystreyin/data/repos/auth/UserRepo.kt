package com.technzone.mystreyin.data.repos.auth

import com.technzone.mystreyin.data.api.response.ResponseWrapper
import com.technzone.mystreyin.data.enums.UserEnums
import com.technzone.mystreyin.data.models.auth.login.UserDetailsResponseModel
import io.reactivex.Single
import okhttp3.MultipartBody


interface UserRepo {
//
//
//    fun login(
//        userName: String,
//        password: String
//    ): Single<ResponseWrapper<UserDetailsResponseModel>>
//
//    fun register(
//        email: String,
//        firstName: String,
//        lastName: String,
//        password: String,
//        phoneNumber: String,
//        registrationId: String,
//        dateOfBirth: String,
//        deviceType: Int,
//        gender: Int,
//        applicationType: Int
//    ): Single<ResponseWrapper<String>>
//
//    fun verifyVerificationCode(
//        userId: String,
//        verificationCode: String
//    ): Single<ResponseWrapper<UserDetailsResponseModel>>
//
//    fun recoveryPassword(
//        newPassword: String
//    ): Single<ResponseWrapper<Any>>
//
//    fun forgetPassword(email: String): Single<ResponseWrapper<String>>
//
//    fun updatePassword(
//        oldPassword: String,
//        newPassword: String
//    ): Single<ResponseWrapper<Any>>
//
//    fun updateProfile(
//        firstName: String,
//        lastName: String,
//        dateOfBirth: String,
//        gender: Int,
//        phoneNumber:String
//    ): Single<ResponseWrapper<Any>>
//
//    fun updateProfilePicture(
//        image:MultipartBody.Part
//    ): Single<ResponseWrapper<Any>>
//
//    fun updateFcmToken(
//        registrationId: String
//    ): Single<ResponseWrapper<Any>>
//
//
//    fun updateAccessToken(
//        refreshToken: String
//    ): Single<ResponseWrapper<UserDetailsResponseModel>>
//
//    fun getProfile(): Single<ResponseWrapper<UserDetailsResponseModel>>
//
//    fun saveNotificationStatus(flag: Boolean)
//    fun getNotificationStatus(): Boolean
//
//    fun saveTouchIdStatus(flag: Boolean)
//    fun getTouchIdStatus(): Boolean
//
//    fun saveUserFirstName(firstName: String)
//    fun getUserFirstName(): String
//
//    fun saveUserSecondName(secondName: String)
//    fun getUserSecondName(): String
//
//    fun saveUserThirdName(thirdName: String)
//    fun getUserThirdName(): String
//
//    fun saveUserLastName(lastName: String)
//    fun getUserLastName(): String
//
//    fun saveAccessToken(accessToken: String)
//    fun getAccessToken(): String
//
//    fun saveUserPhoneNumber(phoneNumber: String)
//    fun getUserPhoneNumber(): String
//
//    fun saveUserPassword(password: String)
//    fun getUserPassword(): String
//
//    fun setUserStatus(userState: UserEnums.UserState)
//    fun getUserStatus(): UserEnums.UserState
//
//    fun setUserId(id: String)
//    fun getUserId(): String
//
//    fun setUserEmailAddress(email: String)
//    fun getUserEmailAddress(): String
//
//    fun setUserCountry(country: String)
//    fun getUserCountry(): String
//
//    fun setUserCity(city: String)
//    fun getUserCity(): String
//
//    fun setUserStreet(street: String)
//    fun getUserStreet(): String
//
//    fun setUserBuilding(building: String)
//    fun getUserBuilding(): String
//
//    fun setUserAddress(address: String)
//    fun getUserAddress(): String
//
//    fun setUserNationalNumber(nationalNumber: String)
//    fun getUserNationalNumber(): String
//
//    fun setUserIdentityType(identityType: String)
//    fun getUserIdentityType(): String
//
//    fun setUserIdentityNumber(identityNumber: String)
//    fun getUserIdentityNumber(): String
//
//    fun setUserIdentityIssueCountry(country: String)
//    fun getUserIdentityIssueCountry(): String
//
//    fun setUserIdentityIssuePlace(place: String)
//    fun getUserIdentityIssuePlace(): String
//
//    fun setUserIdentityIssueData(date: String)
//    fun getUserIdentityIssueData(): String
//
//    fun setUserIdentityExpireDate(date: String)
//    fun getUserIdentityExpireDate(): String
//
//    fun setUserResident(resident: Boolean)
//    fun getUserResident(): Boolean
//
//    fun setUserPlaceOfBirth(place: String)
//    fun getUserPlaceOfBirth(): String
//
//    fun setUserBirthOfDate(date: String)
//    fun getUserBirthOfDate(): String
//
//    fun setUserCountryOfBirth(country: String)
//    fun getUserCountryOfBirth(): String
//
//    fun setUserNationality(nationality: String)
//    fun getUserNationality(): String
//
//    fun setUserOccupation(occupation: String)
//    fun getUserOccupation(): String
//
//    fun setUser(user: UserDetailsResponseModel)
//    fun getUser(): UserDetailsResponseModel?
//    suspend fun verifyLogin(
//        value: String?,
//        customerId: String?
//    ): APIResource<ResponseWrapper<String>>
}