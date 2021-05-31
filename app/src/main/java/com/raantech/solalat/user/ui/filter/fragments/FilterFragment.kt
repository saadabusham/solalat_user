package com.raantech.solalat.user.ui.filter.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.databinding.FragmentFilterBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.filter.viewmodels.FiltersViewModel
import com.raantech.solalat.user.ui.media.MediaActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class FilterFragment : BaseBindingFragment<FragmentFilterBinding>(),
        BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: FiltersViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_filter
    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
                hasToolbar = true,
                hasBackButton = true,
                showBackArrow = true,
                toolbarView = toolbar,
                hasTitle = true,
                title = R.string.search,
                hasSubTitle = false
        )
        setUpBinding()
        setUpListeners()
        init()
    }

    private fun init() {

    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {

    }

    override fun onItemClick(view: View?, position: Int, item: Any) {

    }

}