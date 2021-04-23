package com.saad.base.ui.auth.language

import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.saad.base.R
import com.saad.base.common.CommonEnums
import com.saad.base.databinding.FragmentLanguageBinding
import com.saad.base.ui.base.activity.BaseBindingActivity
import com.saad.base.ui.base.fragment.BaseBindingFragment
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
                    navigationController.navigate(R.id.action_languageFragment_to_onBoardingFragment)
                }
            })
        }

    }
}