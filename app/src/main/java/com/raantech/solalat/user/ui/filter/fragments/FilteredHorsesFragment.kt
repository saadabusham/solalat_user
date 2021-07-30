package com.raantech.solalat.user.ui.filter.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.databinding.FragmentFilteredBarnBinding
import com.raantech.solalat.user.databinding.FragmentFilteredHorseBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.filter.viewmodels.FiltersViewModel
import com.raantech.solalat.user.ui.horse.HorseActivity
import com.raantech.solalat.user.ui.main.adapters.horse.HorsesGridRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class FilteredHorsesFragment : BaseBindingFragment<FragmentFilteredHorseBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_filtered_horse

    private val viewModel: FiltersViewModel by activityViewModels()

    lateinit var horsesGridRecyclerAdapter: HorsesGridRecyclerAdapter

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
        horsesGridRecyclerAdapter = HorsesGridRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = horsesGridRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
        horsesGridRecyclerAdapter.submitItems(viewModel.horsesResults)
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Horse
        item.id?.let { HorseActivity.start(requireContext(), it) }
    }

}