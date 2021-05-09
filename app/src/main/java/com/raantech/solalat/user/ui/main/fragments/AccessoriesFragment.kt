package com.raantech.solalat.user.ui.main.fragments

import android.view.View
import androidx.fragment.app.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.GeneralError
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.ServiceTypesEnum
import com.raantech.solalat.user.data.models.ServiceCategoriesResponse
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.databinding.FragmentAccessoriesBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.main.adapters.CategoryRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccessoriesFragment : BaseBindingFragment<FragmentAccessoriesBinding>(),
        BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_accessories

    private val viewModel: MainViewModel by viewModels()
    lateinit var categoryRecyclerAdapter: CategoryRecyclerAdapter
    var refreshData: Boolean = false

    override fun onResume() {
        super.onResume()
        if (!refreshData) {
            refreshData = true
            return
        }
        categoryRecyclerAdapter.clear()
        loadCategories()
    }

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
        init()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {

    }

    private fun init() {
        setUpRecyclerView()
        loadCategories()
    }


    private fun loadCategories() {
        viewModel.getServicesCategories(ServiceTypesEnum.ACCESSORIES.value).observe(this, categoriesObserver())
    }

    private fun setUpRecyclerView() {
        categoryRecyclerAdapter = CategoryRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = categoryRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
    }

    private fun hideShowNoData() {
        if (categoryRecyclerAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.linearNoData?.visible()
        } else {
            binding?.layoutNoData?.linearNoData?.gone()
            binding?.recyclerView?.visible()
        }
    }


    private fun categoriesObserver(): CustomObserverResponse<ServiceCategoriesResponse> {
        return CustomObserverResponse(
                requireActivity(),
                object : CustomObserverResponse.APICallBack<ServiceCategoriesResponse> {
                    override fun onSuccess(
                            statusCode: Int,
                            subErrorCode: ResponseSubErrorsCodeEnum,
                            data: ResponseWrapper<ServiceCategoriesResponse>?
                    ) {
                        data?.body?.categories?.let {
                            categoryRecyclerAdapter.addItems(it)
                        }
                        hideShowNoData()
                    }

                    override fun onError(
                            subErrorCode: ResponseSubErrorsCodeEnum,
                            message: String,
                            errors: List<GeneralError>?
                    ) {
                        super.onError(subErrorCode, message, errors)
//                        hideShowNoData()
                        categoryRecyclerAdapter.submitItems(
                                arrayListOf(
                                        ServiceCategory(id = 0, name = "حذوات الخيل", count = 5, logo = null),
                                        ServiceCategory(id = 0, name = "سراج الخيل", count = 5, logo = null),
                                        ServiceCategory(id = 0, name = "لجام الخيل", count = 5, logo = null)
                                )
                        )
                    }
                },showError = false
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {

    }

}