package com.raantech.solalat.user.data.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseWrapper<RETURN_MODEL>(
    @field:SerializedName("errors")
    val errors: List<GeneralError>,
    @field:SerializedName("code")
    val code: Int,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("body")
    val body: RETURN_MODEL?
) : Serializable