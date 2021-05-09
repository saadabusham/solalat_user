package com.raantech.solalat.user.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ServicesItem(

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int,
        var selected: Boolean = false
):Serializable
