package com.raantech.solalat.user.data.models.auth.login

import com.squareup.moshi.Json
import java.io.Serializable

data class TokenModel(
    @field:Json(name = "token")
    val token: String? = null
): Serializable