package com.raantech.solalat.user.ui.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.raantech.solalat.user.R
import com.raantech.solalat.user.databinding.ActivityAddHorseBinding
import com.raantech.solalat.user.databinding.ActivityFiltersBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersActivity : BaseBindingActivity<ActivityFiltersBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters, hasToolbar = false)

    }

    companion object {
        fun start(
                context: Context?
        ) {
            val intent = Intent(context, FiltersActivity::class.java)
            context?.startActivity(intent)
        }
    }

}