package com.raantech.solalat.user.data.models.horses.horsesubscription

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IncreaseResponse(

	@field:SerializedName("minimum_bid")
	val minimumBid: MinimumBid? = null
): Serializable