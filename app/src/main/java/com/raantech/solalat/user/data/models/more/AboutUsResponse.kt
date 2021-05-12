package com.raantech.solalat.user.data.models.more

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AboutUsResponse(

	@field:SerializedName("about_us")
	val aboutUs: String? = null
): Serializable