package com.raantech.solalat.user.ui.filter.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.databinding.FragmentFilteredHorseBinding
import com.raantech.solalat.user.databinding.FragmentFilteredTrucksBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.filter.viewmodels.FiltersViewModel
import com.raantech.solalat.user.ui.horse.HorseActivity
import com.raantech.solalat.user.ui.main.adapters.horse.HorsesGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.truck.TrucksGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.truck.activities.TruckDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class FilteredTrucksFragment : BaseBindingFragment<FragmentFilteredTrucksBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_filtered_trucks

    private val viewModel: FiltersViewModel by activityViewModels()

    lateinit var trucksGridRecyclerAdapter: TrucksGridRecyclerAdapter

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
        trucksGridRecyclerAdapter = TrucksGridRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = trucksGridRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
        trucksGridRecyclerAdapter.submitItems(viewModel.truckResults)
    }


    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Truck
        item.id?.let { TruckDetailsActivity.start(requireContext(), it) }
    }

}