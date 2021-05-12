package com.raantech.solalat.user.data.models.accessories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.raantech.solalat.user.data.db.ApplicationDB
import com.raantech.solalat.user.data.models.Price
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.media.Media
import java.io.Serializable

@Entity(tableName = ApplicationDB.TABLE_CART)
data class Accessory(

	@field:SerializedName("additional_images")
	@ColumnInfo(name = "additional_images")
	val additionalImages: List<Media?>? = null,

	@field:SerializedName("is_active")
	@ColumnInfo(name = "is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("description")
	@ColumnInfo(name = "description")
	val description: String? = null,

	@field:SerializedName("created_at")
	@ColumnInfo(name = "created_at")
	val createdAt: String? = null,

	@field:SerializedName("contact_number")
	@ColumnInfo(name = "contact_number")
	val contactNumber: String? = null,

	@field:SerializedName("is_wishlist")
	@ColumnInfo(name = "is_wishlist")
	var isWishlist: Boolean? = null,

	@field:SerializedName("base_image")
	@ColumnInfo(name = "base_image")
	val baseImage: Media? = null,

	@field:SerializedName("price")
	@ColumnInfo(name = "price")
	val price: Price? = null,

	@field:SerializedName("name")
	@ColumnInfo(name = "name")
	val name: String? = null,

	@field:SerializedName("is_approved")
	@ColumnInfo(name = "is_approved")
	val isApproved: Boolean? = null,

	@PrimaryKey(autoGenerate = false)
	@field:SerializedName("id")
	@ColumnInfo(name = "id")
	val id: Int? = null,

	@field:SerializedName("category")
	@ColumnInfo(name = "category")
	val category: ServiceCategory? = null,

	@field:SerializedName("received_whatsapp")
	@ColumnInfo(name = "received_whatsapp")
	val receivedWhatsapp: Boolean? = null,

	@field:SerializedName("count")
	@ColumnInfo(name = "count")
	var count: Int? = 1
) : Serializable