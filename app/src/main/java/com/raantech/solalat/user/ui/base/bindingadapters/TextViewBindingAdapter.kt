package com.raantech.solalat.user.ui.base.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.raantech.solalat.user.utils.DateTimeUtil.getDifferenceTime


@BindingAdapter(
    value = ["end_date"],
    requireAll = true
)
fun TextView.setDifferenceTime(start_date:Long ,end_date: Long) {
    this.text = this.context.getDifferenceTime(start_date,end_date)
}

