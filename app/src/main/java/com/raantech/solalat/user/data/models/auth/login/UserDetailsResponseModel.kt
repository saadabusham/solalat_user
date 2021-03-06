package com.raantech.solalat.user.data.models.auth.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDetailsResponseModel(

	@field:SerializedName("user_info")
	val userInfo: UserInfo? = null,

	@field:SerializedName("auth_token")
	val authToken: String? = null
): Serializable