package com.raantech.solalat.user.data.models.accessories

import com.google.gson.annotations.SerializedName
import com.raantech.solalat.user.data.models.Price
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.media.Media

data class Accessory(

	@field:SerializedName("additional_images")
	val additionalImages: List<Media?>? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("contact_number")
	val contactNumber: String? = null,

	@field:SerializedName("is_wishlist")
    var isWishlist: Boolean? = null,

	@field:SerializedName("base_image")
	val baseImage: Media? = null,

	@field:SerializedName("price")
	val price: Price? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("is_approved")
	val isApproved: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: ServiceCategory? = null,

	@field:SerializedName("received_whatsapp")
	val receivedWhatsapp: Boolean? = null
)