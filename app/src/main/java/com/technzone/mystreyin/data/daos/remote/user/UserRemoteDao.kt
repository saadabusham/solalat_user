package com.technzone.mystreyin.data.daos.remote.user

interface UserRemoteDao {

//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
//    @FormUrlEncoded
//    @POST("api/user/authenticate")
//    fun login(
//        @Field("Username") userName: String,
//        @Field("Password") password: String
//    ): Single<ResponseWrapper<UserDetailsResponseModel>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
//    @FormUrlEncoded
//    @POST("api/user/register")
//    fun register(
//        @Field("Email") email: String,
//        @Field("FirstName") firstName: String,
//        @Field("LastName") lastName: String,
//        @Field("Password") password: String,
//        @Field("PhoneNumber") phoneNumber: String,
//        @Field("RegistrationId") registrationId: String,
//        @Field("DateOfBirth") dateOfBirth: String,
//        @Field("DeviceType") deviceType: Int,
//        @Field("Gender") gender: Int,
//        @Field("ApplicationType") applicationType: Int
//    ): Single<ResponseWrapper<String>>
//
//    @FormUrlEncoded
//    @POST("api/user/verify")
//    fun verifyVerificationCode(
//        @Field("UserId") userId: String,
//        @Field("VerificationCode") verificationCode: String
//    ): Single<ResponseWrapper<UserDetailsResponseModel>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
//    @POST("api/user/forgetPassword/{email}")
//    fun forgetPassword(
//        @Path("email") emailParam: String
//    ): Single<ResponseWrapper<String>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
//    @FormUrlEncoded
//    @POST("api/user/newPassword")
//    fun recoveryPassword(
//        @Field("Password") newPassword: String
//    ): Single<ResponseWrapper<Any>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
//    @FormUrlEncoded
//    @POST("api/user/newPassword")
//    fun updatePassword(
//        @Field("OldPassword") OldPassword: String,
//        @Field("Password") NewPassword: String
//    ): Single<ResponseWrapper<Any>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
//    @Multipart
//    @PATCH("api/user/update")
//    fun updateProfile(
//        @Part("FirstName") firstName: RequestBody,
//        @Part("LastName") lastName: RequestBody,
//        @Part("DateOfBirth") dateOfBirth: RequestBody,
//        @Part("Gender") gender: RequestBody,
//        @Part("PhoneNumber") phoneNumber: RequestBody
//    ): Single<ResponseWrapper<Any>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
//    @Multipart
//    @PATCH("api/user/update")
//    fun updateProfilePicture(
//        @Part image: MultipartBody.Part
//    ): Single<ResponseWrapper<Any>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
//    @GET("api/user")
//    fun profile(): Single<ResponseWrapper<UserDetailsResponseModel>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
//    @Multipart
//    @PATCH("api/user/update")
//    fun updateFcmToken(
//        @Part("RegistrationId") registrationId: RequestBody,
//        @Part("DeviceType") deviceType: RequestBody
//    ): Single<ResponseWrapper<Any>>
//
//    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
//    @FormUrlEncoded
//    @POST("api/user/refreshToken")
//    fun updateAccessToken(
//        @Field("RefreshToken") refreshToken: String
//    ): Single<ResponseWrapper<UserDetailsResponseModel>>
}