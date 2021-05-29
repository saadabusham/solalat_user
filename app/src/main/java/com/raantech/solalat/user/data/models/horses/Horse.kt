package com.raantech.solalat.user.data.models.horses

import com.google.gson.annotations.SerializedName
import com.raantech.solalat.user.data.enums.HorseAdsTypeEnum
import com.raantech.solalat.user.data.models.Price
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.utils.DateTimeUtil.getDifferenceTime
import com.raantech.solalat.user.utils.LocaleUtil
import com.raantech.solalat.user.utils.extensions.getCurrentDate
import com.raantech.solalat.user.utils.extensions.toMillieSecconds
import java.io.Serializable

data class Horse(

	@field:SerializedName("is_vaccinated")
	val isVaccinated: Boolean? = null,

	@field:SerializedName("additional_images")
	val additionalImages: List<Media>? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("start_date_auction")
	val startDateAuction: String? = null,

	@field:SerializedName("sex")
	val sex: String? = null,

	@field:SerializedName("mother_name")
	val motherName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("contact_number")
	val contactNumber: String? = null,

	@field:SerializedName("type_of_sale")
	val typeOfSale: String? = null,

	@field:SerializedName("end_date_auction")
	val endDateAuction: String? = null,

	@field:SerializedName("base_image")
	val baseImage: Media? = null,

	@field:SerializedName("father_name")
	val fatherName: String? = null,

	@field:SerializedName("safety")
	val safety: String? = null,

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

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("received_whatsapp")
	val receivedWhatsapp: Int? = null,

	@field:SerializedName("height")
	val height: String? = null,

	@field:SerializedName("is_wishlist")
	var is_wishlist: Boolean? = null
) : Serializable {
    fun action(): String {
        return if (typeOfSale == HorseAdsTypeEnum.SELL.value) price?.formatted ?: ""
        else getDifferenceTime(
			getCurrentDate().toMillieSecconds(),
			endDateAuction.toMillieSecconds()
		)
    }

    fun millisUntilFinish(): Long {
        return endDateAuction.toMillieSecconds() - getCurrentDate().toMillieSecconds()
    }

    fun horseTitle(): String {
        return when (LocaleUtil.getLanguage() == "ar") {
			true -> {
				"$age"+"سنوات"+"/"+category?.name
			}
            else -> {
                "$age years /${category?.name}"
            }
        }
    }
}