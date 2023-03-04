package com.raantech.solalat.user.data.models.auth.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RefreshToken(

    @field:SerializedName("validUntil")
    val validUntil: String? = null,

    @field:SerializedName("refreshToken")
    val refreshToken: String? = null
):Serializable