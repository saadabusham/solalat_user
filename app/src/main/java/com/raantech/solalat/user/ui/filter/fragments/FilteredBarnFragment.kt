package com.raantech.solalat.user.ui.filter.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.databinding.FragmentFilteredBarnBinding
import com.raantech.solalat.user.databinding.FragmentFilteredHorseBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.filter.viewmodels.FiltersViewModel
import com.raantech.solalat.user.ui.main.adapters.barn.BarnGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.barn.activities.BarnDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class FilteredBarnFragment : BaseBindingFragment<FragmentFilteredBarnBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_filtered_barn

    private val viewModel: FiltersViewModel by activityViewModels()

    lateinit var barnGridRecyclerAdapter: BarnGridRecyclerAdapter

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.search_result
        )
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        barnGridRecyclerAdapter = BarnGridRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = barnGridRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
        barnGridRecyclerAdapter.submitItems(viewModel.stableResults)
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Barn
        item.id?.let { BarnDetailsActivity.start(requireContext(), it) }
    }

}