package com.raantech.solalat.user.ui.auth.language

import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.common.CommonEnums
import com.raantech.solalat.user.databinding.FragmentLanguageBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseBindingFragment<FragmentLanguageBinding>() {

    private val viewModel: LanguageViewModel by navGraphViewModels(R.id.auth_nav_graph) { defaultViewModelProviderFactory }

    override fun getLayoutId(): Int = R.layout.fragment_language

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.btnContinue?.setOnClickListener {
            viewModel.saveLanguage().observe(viewLifecycleOwner, Observer {
                activity?.let {
                    (it as BaseBindingActivity<*>).setLanguage(if (viewModel.englishSelected.value!!)
                       CommonEnums.Languages.English.value else CommonEnums.Languages.Arabic.value)
                    navigationController.navigate(R.id.action_languageFragment_to_loginFragment)
                }
            })
        }

    }
}