package com.raantech.solalat.user.ui.horse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.databinding.ActivityHorseBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.horse.viewmodels.HorseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorseActivity : BaseBindingActivity<ActivityHorseBinding>() {

    private val viewModel: HorseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.horse = intent.getSerializableExtra(Constants.BundleData.HORSE) as Horse
        setContentView(R.layout.activity_horse, hasToolbar = false)
    }

    companion object {
        fun start(
                context: Context?,
                horse: Horse
        ) {
            val intent = Intent(context, HorseActivity::class.java)
            intent.putExtra(Constants.BundleData.HORSE, horse)
            context?.startActivity(intent)
        }
    }

}