package com.raantech.solalat.user.data.models.horses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Files(
		@field:SerializedName("additional_images")
		val additionalImages: List<Int>? = null,

		@field:SerializedName("base_image")
		val baseImage: Int? = null
) : Serializable