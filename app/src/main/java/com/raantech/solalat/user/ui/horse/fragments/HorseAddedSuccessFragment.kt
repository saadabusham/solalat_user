package com.raantech.solalat.user.ui.horse.fragments

import com.raantech.solalat.user.R
import com.raantech.solalat.user.databinding.FragmentHorseAddedSuccessBinding
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorseAddedSuccessFragment :
    BaseBindingFragment<FragmentHorseAddedSuccessBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_horse_added_success

    override fun onViewVisible() {
        super.onViewVisible()
        setUpViewsListeners()
    }

    private fun setUpViewsListeners() {
        binding?.btnDone?.setOnClickListener {
            requireActivity().finish()
        }
    }
}