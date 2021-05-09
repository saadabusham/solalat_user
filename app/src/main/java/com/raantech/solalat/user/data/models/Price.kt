package com.raantech.solalat.user.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Price(

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("formatted")
	val formatted: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null
):Serializable