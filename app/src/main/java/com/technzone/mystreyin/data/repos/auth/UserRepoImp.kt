package com.technzone.mystreyin.data.repos.auth

import com.technzone.mystreyin.data.api.response.ResponseHandler
import com.technzone.mystreyin.data.api.response.ResponseWrapper
import com.technzone.mystreyin.data.common.Constants
import com.technzone.mystreyin.data.daos.remote.user.UserRemoteDao
import com.technzone.mystreyin.data.enums.UserEnums
import com.technzone.mystreyin.data.pref.user.UserPref
import com.technzone.mystreyin.data.models.auth.login.UserDetailsResponseModel
import com.technzone.mystreyin.data.repos.auth.UserRepo
import com.technzone.mystreyin.data.repos.base.BaseRepo
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepoImp @Inject constructor(
    private val userRemoteDao: UserRemoteDao,
    private val userPref: UserPref
) : BaseRepo(), UserRepo {

//
//    override fun login(
//        userName: String,
//        password: String
//    ): Single<ResponseWrapper<UserDetailsResponseModel>> {
//        return userRemoteDao.login(
//            userName,
//            password
//        )
//    }
//
//
//    override fun register(
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
//    ): Single<ResponseWrapper<String>> {
//        return userRemoteDao.register(
//            email,
//            firstName,
//            lastName,
//            password,
//            phoneNumber,
//            registrationId,
//            dateOfBirth,
//            deviceType,
//            gender,
//            applicationType
//        )
//    }
//
//    override fun verifyVerificationCode(userID: String, verificationCode: String):
//            Single<ResponseWrapper<UserDetailsResponseModel>> {
//        return userRemoteDao.verifyVerificationCode(userID, verificationCode)
//    }
//
//    override fun recoveryPassword(
//        newPassword: String
//    ): Single<ResponseWrapper<Any>> {
//        return userRemoteDao.recoveryPassword(newPassword)
//    }
//
//    override fun forgetPassword(email: String): Single<ResponseWrapper<String>> {
//        return userRemoteDao.forgetPassword(email)
//    }
//
//    override fun updatePassword(
//        oldPassword: String,
//        newPassword: String
//    ): Single<ResponseWrapper<Any>> {
//        return userRemoteDao.updatePassword(oldPassword, newPassword)
//    }
//
//    override fun updateProfile(
//        firstName: String,
//        lastName: String,
//        dateOfBirth: String,
//        gender: Int,
//        phoneNumber:String
//    ): Single<ResponseWrapper<Any>> {
//        return userRemoteDao.updateProfile(
//            firstName.getRequestBody(),
//            lastName.getRequestBody(),
//            dateOfBirth.getRequestBody(),
//            gender.toString().getRequestBody(),
//            phoneNumber.getRequestBody()
//        )
//    }
//
//    override fun updateProfilePicture(image: MultipartBody.Part): Single<ResponseWrapper<Any>> {
//        return userRemoteDao.updateProfilePicture(image)
//    }
//
//    override fun updateFcmToken(registrationId: String): Single<ResponseWrapper<Any>> {
//        return userRemoteDao.updateFcmToken(
//            registrationId.getRequestBody(),
//            Constants.DEVICE_TYPE.toString().getRequestBody()
//        )
//    }
//
//    override fun updateAccessToken(refreshToken: String): Single<ResponseWrapper<UserDetailsResponseModel>> {
//        return userRemoteDao.updateAccessToken(refreshToken)
//    }
//
//    override fun getProfile(): Single<ResponseWrapper<UserDetailsResponseModel>> {
//        return userRemoteDao.profile()
//    }
//
//    override fun saveAccessToken(accessToken: String) {
//        userPref.saveAccessToken(accessToken)
//    }
//
//    override fun getAccessToken(): String {
//        return userPref.getAccessToken()
//    }
//
//
//    override fun saveUserPhoneNumber(phoneNumber: String) {
//        userPref.saveUserPhoneNumber(phoneNumber)
//    }
//
//    override fun getUserPhoneNumber(): String {
//        return userPref.getUserPhoneNumber()
//    }
//
//    override fun saveUserPassword(password: String) {
//        userPref.saveUserPassword(password)
//    }
//
//    override fun getUserPassword(): String {
//        return userPref.getUserPassword()
//    }
//
//    override fun setUserStatus(userState: UserEnums.UserState) {
//        userPref.setUserStatus(userState.ordinal)
//    }
//
//    override fun getUserStatus(): UserEnums.UserState {
//        return UserEnums.UserState.getUserStateByPosition(userPref.getUserStatus())
//    }
//
//
//    override fun saveNotificationStatus(flag: Boolean) {
//        userPref.setNotificationStatus(flag)
//    }
//
//    override fun getNotificationStatus(): Boolean {
//        return userPref.getNotificationStatus()
//    }
//
//    override fun saveTouchIdStatus(flag: Boolean) {
//        userPref.setTouchIdStatus(flag)
//    }
//
//    override fun getTouchIdStatus(): Boolean {
//        return userPref.getTouchIdStatus()
//    }
//
//    override fun saveUserFirstName(firstName: String) {
//        userPref.saveUserFirstName(firstName)
//    }
//
//    override fun getUserFirstName(): String {
//        return userPref.getUserFirstName()
//    }
//
//    override fun saveUserSecondName(secondName: String) {
//        userPref.saveUserSecondName(secondName)
//    }
//
//    override fun getUserSecondName(): String {
//        return userPref.getUserSecondName()
//    }
//
//    override fun saveUserThirdName(thirdName: String) {
//        userPref.saveUserThirdName(thirdName)
//    }
//
//    override fun getUserThirdName(): String {
//        return userPref.getUserThirdName()
//    }
//
//    override fun saveUserLastName(lastName: String) {
//        userPref.saveUserLastName(lastName)
//    }
//
//    override fun getUserLastName(): String {
//        return userPref.getUserLastName()
//    }
//
//    override fun setUserId(id: String) {
//        userPref.setUserId(id)
//    }
//
//    override fun getUserId(): String {
//        return userPref.getUserId()
//    }
//
//    override fun setUserEmailAddress(email: String) {
//        userPref.setUserEmailAddress(email)
//    }
//
//    override fun getUserEmailAddress(): String {
//        return userPref.getUserEmailAddress()
//    }
//
//    override fun setUserCountry(country: String) {
//        userPref.setUserCountry(country)
//    }
//
//    override fun getUserCountry(): String {
//        return userPref.getUserCountry()
//    }
//
//    override fun setUserCity(city: String) {
//        userPref.setUserCity(city)
//    }
//
//    override fun getUserCity(): String {
//        return userPref.getUserCity()
//    }
//
//    override fun setUserStreet(street: String) {
//        userPref.setUserStreet(street)
//    }
//
//    override fun getUserStreet(): String {
//        return userPref.getUserStreet()
//    }
//
//    override fun setUserBuilding(building: String) {
//        userPref.setUserBuilding(building)
//    }
//
//    override fun getUserBuilding(): String {
//        return userPref.getUserBuilding()
//    }
//
//    override fun setUserAddress(address: String) {
//        userPref.setUserAddress(address)
//    }
//
//    override fun getUserAddress(): String {
//        return userPref.getUserAddress()
//    }
//
//    override fun setUserNationalNumber(nationalNumber: String) {
//        userPref.setUserNationalNumber(nationalNumber)
//    }
//
//    override fun getUserNationalNumber(): String {
//        return userPref.getUserNationalNumber()
//    }
//
//    override fun setUserIdentityType(identityType: String) {
//        userPref.setUserIdentityType(identityType)
//    }
//
//    override fun getUserIdentityType(): String {
//        return userPref.getUserIdentityType()
//    }
//
//    override fun setUserIdentityNumber(identityNumber: String) {
//        userPref.setUserIdentityNumber(identityNumber)
//    }
//
//    override fun getUserIdentityNumber(): String {
//        return userPref.getUserIdentityNumber()
//    }
//
//    override fun setUserIdentityIssueCountry(country: String) {
//        userPref.setUserIdentityIssueCountry(country)
//    }
//
//    override fun getUserIdentityIssueCountry(): String {
//        return userPref.getUserIdentityIssueCountry()
//    }
//
//    override fun setUserIdentityIssuePlace(place: String) {
//        userPref.setUserIdentityIssuePlace(place)
//    }
//
//    override fun getUserIdentityIssuePlace(): String {
//        return userPref.getUserIdentityIssuePlace()
//    }
//
//    override fun setUserIdentityIssueData(date: String) {
//        userPref.setUserIdentityIssueData(date)
//    }
//
//    override fun getUserIdentityIssueData(): String {
//        return userPref.getUserIdentityIssueData()
//    }
//
//    override fun setUserIdentityExpireDate(date: String) {
//        userPref.setUserIdentityExpireDate(date)
//    }
//
//    override fun getUserIdentityExpireDate(): String {
//        return userPref.getUserIdentityExpireDate()
//    }
//
//    override fun setUserResident(resident: Boolean) {
//        userPref.setUserResident(resident)
//    }
//
//    override fun getUserResident(): Boolean {
//        return userPref.getUserResident()
//    }
//
//    override fun setUserPlaceOfBirth(place: String) {
//        userPref.setUserPlaceOfBirth(place)
//    }
//
//    override fun getUserPlaceOfBirth(): String {
//        return userPref.getUserPlaceOfBirth()
//    }
//
//    override fun setUserBirthOfDate(date: String) {
//        userPref.setUserBirthOfDate(date)
//    }
//
//    override fun getUserBirthOfDate(): String {
//        return userPref.getUserBirthOfDate()
//    }
//
//    override fun setUserCountryOfBirth(country: String) {
//        return userPref.setUserCountryOfBirth(country)
//    }
//
//    override fun getUserCountryOfBirth(): String {
//        return userPref.getUserCountryOfBirth()
//    }
//
//    override fun setUserNationality(nationality: String) {
//        userPref.setUserNationality(nationality)
//    }
//
//    override fun getUserNationality(): String {
//        return userPref.getUserNationality()
//    }
//
//    override fun setUserOccupation(occupation: String) {
//        userPref.setUserOccupation(occupation)
//    }
//
//    override fun getUserOccupation(): String {
//        return userPref.getUserOccupation()
//    }
//
//    override fun setUser(user: UserDetailsResponseModel) {
//        userPref.setUser(user)
//    }
//
//    override fun getUser(): UserDetailsResponseModel? {
//        return userPref.getUser()
//    }


//    override suspend fun verifyLogin(
//        value: String?,
//        customerId: String?
//    ): APIResource<ResponseWrapper<String>> {
//        return try {
//            responseHandle.handleSuccess(userRemoteDao.verifyLogin(value, customerId))
//        } catch (e: Exception) {
//            responseHandle.handleException(e)
//        }
//    }
}