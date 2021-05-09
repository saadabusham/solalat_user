package com.raantech.solalat.user.data.models.auth.login

import com.squareup.moshi.Json

data class TokenModel(
    @field:Json(name = "token")
    val token: String? = null
)