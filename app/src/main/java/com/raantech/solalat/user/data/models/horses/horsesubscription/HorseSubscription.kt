package com.raantech.solalat.user.data.models.horses.horsesubscription

import com.google.gson.annotations.SerializedName

data class HorseSubscription(

	@field:SerializedName("redirectUrl")
	val redirectUrl: String? = null,

	@field:SerializedName("modelId")
	val modelId: Int? = null
)