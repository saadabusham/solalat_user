package com.raantech.solalat.user.utils.extensions

import androidx.databinding.BindingAdapter
import com.raantech.solalat.user.R
import com.raantech.solalat.user.common.CommonEnums
import com.raantech.solalat.user.utils.LocaleUtil
import com.skydoves.powerspinner.PowerSpinnerView
import com.skydoves.powerspinner.SpinnerGravity

@BindingAdapter("arrow_gravity")
fun PowerSpinnerView.setArrowGravity(boolean: Boolean) {
   this.arrowGravity = if(LocaleUtil.getLanguage() == CommonEnums.Languages.Arabic.value) SpinnerGravity.START else SpinnerGravity.END
   this.arrowTint = resources.getColor(R.color.circle_image_stroke_color)
}

