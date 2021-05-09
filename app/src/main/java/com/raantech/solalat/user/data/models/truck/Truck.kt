package com.raantech.solalat.user.data.models.truck

import com.google.gson.annotations.SerializedName
import com.raantech.solalat.user.data.models.City
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.media.Media
import java.io.Serializable

data class Truck(

		@field:SerializedName("additional_images")
		val additionalImages: List<Media>? = null,

		@field:SerializedName("address")
		val address: String? = null,

		@field:SerializedName("is_active")
		val isActive: Boolean? = null,

		@field:SerializedName("cities")
		val cities: List<City>? = null,

		@field:SerializedName("distance")
		val distance: Double? = null,

		@field:SerializedName("latitude")
		val latitude: String? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("truck_number")
		val truckNumber: String? = null,

		@field:SerializedName("contact_number")
		val contactNumber: String? = null,

		@field:SerializedName("manufacturing_year")
		val manufacturingYear: String? = null,

		@field:SerializedName("base_image")
		val baseImage: Media? = null,

		@field:SerializedName("available_it")
		val availableIt: Boolean? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("is_approved")
		val isApproved: Boolean? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("category")
		val category: ServiceCategory? = null,

		@field:SerializedName("received_whatsapp")
		val receivedWhatsapp: Boolean? = null,

		@field:SerializedName("longitude")
		val longitude: String? = null,

		@field:SerializedName("is_wishlist")
		var is_wishlist: Boolean? = null
):Serializable