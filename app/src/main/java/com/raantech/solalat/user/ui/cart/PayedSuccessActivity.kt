package com.raantech.solalat.user.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.databinding.ActivityPayedSuccessBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.cart.viewmodels.CartViewModel
import com.raantech.solalat.user.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_favorite_toolbar.*

@AndroidEntryPoint
class PayedSuccessActivity : BaseBindingActivity<ActivityPayedSuccessBinding>() {

    private val viewModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_payed_success,
            hasToolbar = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.purchas_completed,
            hasBackButton = true,
            showBackArrow = true
        )
        setUpBinding()
        setUpViewsListeners()
    }

    private fun setUpBinding() {
//        binding?.viewModel = viewModel
    }

    private fun setUpViewsListeners() {
        binding?.btnDone?.setOnClickListener {
            MainActivity.start(this)
        }
    }

    companion object {
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, PayedSuccessActivity::class.java)
            context?.startActivity(intent)
        }
    }

}