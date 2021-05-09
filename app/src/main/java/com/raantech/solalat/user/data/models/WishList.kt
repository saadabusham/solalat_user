package com.raantech.solalat.user.data.models

import com.google.gson.annotations.SerializedName

data class WishList(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null
)