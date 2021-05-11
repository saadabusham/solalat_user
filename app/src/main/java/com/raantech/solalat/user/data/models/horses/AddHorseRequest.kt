package com.raantech.solalat.user.data.models.horses

import com.google.gson.annotations.SerializedName

data class AddHorseRequest(

	@field:SerializedName("is_vaccinated")
	val isVaccinated: Boolean? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("sex")
	val sex: String? = null,

	@field:SerializedName("mother_name")
	val motherName: String? = null,

	@field:SerializedName("contact_number")
	val contactNumber: String? = null,

	@field:SerializedName("type_of_sale")
	val typeOfSale: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("father_name")
	val fatherName: String? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("safety")
	val safety: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("files")
	val files: Files? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("received_whatsapp")
	val receivedWhatsapp: Boolean? = null,

	@field:SerializedName("height")
	val height: String? = null
)