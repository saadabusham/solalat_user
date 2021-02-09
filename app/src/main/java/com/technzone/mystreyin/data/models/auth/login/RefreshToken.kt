package com.technzone.mystreyin.data.models.auth.login

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class RefreshToken(

	@field:Json(name ="validUntil")
	val validUntil: String? = null,

	@field:Json(name ="refreshToken")
	val refreshToken: String? = null
)