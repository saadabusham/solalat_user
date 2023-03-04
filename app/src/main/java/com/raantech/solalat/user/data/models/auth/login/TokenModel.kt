package com.raantech.solalat.user.data.models.auth.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TokenModel(
    @field:SerializedName("token")
    val token: String? = null
) : Serializable