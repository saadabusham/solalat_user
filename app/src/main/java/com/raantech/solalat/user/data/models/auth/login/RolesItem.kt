package com.raantech.solalat.user.data.models.auth.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RolesItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Serializable