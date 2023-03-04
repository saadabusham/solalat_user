package com.raantech.solalat.user.data.models.horses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HorseDetails(

    @field:SerializedName("extra_data")
    val extraData: HorseExtraData? = null,

    @field:SerializedName("horse")
    val horse: Horse? = null

) : Serializable