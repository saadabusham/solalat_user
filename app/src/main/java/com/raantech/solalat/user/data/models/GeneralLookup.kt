package com.raantech.solalat.user.data.models

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GeneralLookup(

    @field:SerializedName("Id")
    val id: Int? = null,

    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    var selected: MutableLiveData<Boolean> = MutableLiveData(false)
) : Serializable
