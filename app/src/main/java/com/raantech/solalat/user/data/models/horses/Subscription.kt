package com.raantech.solalat.user.data.models.horses

import com.google.gson.annotations.SerializedName
import com.raantech.solalat.user.data.models.Price
import java.io.Serializable

data class Subscription(

	@field:SerializedName("id")
    var id: Int? = null,

	@field:SerializedName("minimum_bid")
	val minimumBid: Price? = null
):Serializable