package com.raantech.solalat.user.ui.filter.fragments

import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.databinding.FragmentFilteredAccessoriesBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.cart.CartActivity
import com.raantech.solalat.user.ui.filter.viewmodels.FiltersViewModel
import com.raantech.solalat.user.ui.horse.HorseActivity
import com.raantech.solalat.user.ui.main.accessories.dialogs.AccessoriesDialog
import com.raantech.solalat.user.ui.main.adapters.accessories.AccessoriesGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class FilteredAccessoriesFragment : BaseBindingFragment<FragmentFilteredAccessoriesBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_filtered_accessories

    private val viewModel: FiltersViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var accessoriesRecyclerAdapter: AccessoriesGridRecyclerAdapter

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
        setUpBinding()
        setUpListeners()
        setUpRecyclerView()
    }
    private fun setUpBinding() {
        binding?.viewModel = mainViewModel
        mainViewModel.getCartsCount()
    }

    private fun setUpListeners() {
        binding?.relativeCart?.setOnClickListener {
            CartActivity.start(requireContext())
        }
    }
    private fun setUpRecyclerView() {
        accessoriesRecyclerAdapter = AccessoriesGridRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = accessoriesRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
        accessoriesRecyclerAdapter.submitItems(viewModel.accessoriesResults)
    }

    private fun accessoryObserver(position: Int): CustomObserverResponse<Accessory> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Accessory> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Accessory?
                ) {
                    data?.let {
                        AccessoriesDialog(
                            requireActivity(),
                            this@FilteredAccessoriesFragment,
                            it,
                            mainViewModel,
                            object : AccessoriesDialog.CallBack {
                                override fun callBack(position: Int, accessory: Accessory) {
                                    accessoriesRecyclerAdapter.items[position].isWishlist =
                                        accessory.isWishlist
                                }

                            },
                            position
                        ).show()
                    }
                }
            }, true
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Accessory
        if (item.isAvailable == true) {
            item.id?.let { mainViewModel.getAccessory(it).observe(this, accessoryObserver(position)) }
        }
    }

}