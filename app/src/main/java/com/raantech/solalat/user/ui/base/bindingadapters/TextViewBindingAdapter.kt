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
            text = this.context.resources.getString(R.string.status_in_the_way)
            setTextColor(this.context.resources.getColor(R.color.status_in_the_way))
        }
        PurchaseStatusEnum.DELIVERED.value -> {
            text = this.context.resources.getString(R.string.status_delivered)
            setTextColor(this.context.resources.getColor(R.color.status_delivered))
        }
    }
}

