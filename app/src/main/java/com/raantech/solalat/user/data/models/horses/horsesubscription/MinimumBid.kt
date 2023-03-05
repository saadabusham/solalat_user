package com.raantech.solalat.user.data.models.horses.horsesubscription

import com.google.gson.annotations.SerializedName
import com.raantech.solalat.user.data.models.Price
import java.io.Serializable

data class MinimumBid(

    @field:SerializedName("horse_id")
    val horse_id: Int? = null,

    @field:SerializedName("new_price")
    val newPrice: Price? = null,

    @field:SerializedName("previous_price")
    val previousPrice: Price? = null

) : Serializable