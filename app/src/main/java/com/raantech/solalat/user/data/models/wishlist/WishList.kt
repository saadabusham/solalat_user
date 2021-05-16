package com.raantech.solalat.user.data.models.wishlist

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WishList(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("is_wishlist")
	var isWishlist: Boolean? = true
):Serializable