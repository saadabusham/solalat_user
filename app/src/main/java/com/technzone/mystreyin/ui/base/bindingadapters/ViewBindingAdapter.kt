package com.technzone.mystreyin.ui.base.bindingadapters

import android.graphics.Color
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("vHexBackground")
fun View?.setHexBackgroundColor(color: String) {
    this?.setBackgroundColor(Color.parseColor(color))
}