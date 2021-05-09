package com.raantech.solalat.user.data.models

import com.google.gson.annotations.SerializedName

data class ServiceCategoriesResponse(

	@field:SerializedName("categories")
	val categories: List<ServiceCategory>? = null
)