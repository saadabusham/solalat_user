package com.raantech.solalat.user.ui.base.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.enums.PurchaseStatusEnum

@BindingAdapter("status")
fun TextView.setStatus(
    status: String
) {
    when (status) {
        PurchaseStatusEnum.IN_THE_WAY.value -> {
            text = status
            setTextColor(this.context.resources.getColor(R.color.status_in_the_way))
        }
        PurchaseStatusEnum.DELIVERED.value -> {
            text = status
            setTextColor(this.context.resources.getColor(R.color.status_delivered))
        }
    }
}

