package com.raantech.solalat.user.ui.base.views

import android.content.Context
import android.util.AttributeSet
import com.raantech.solalat.user.R


class AppEditText @JvmOverloads constructor(
    context: Context,
    private var attrs: AttributeSet,
    defStyleAttr: Int = R.attr.editTextStyle
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

}