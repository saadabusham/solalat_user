package com.raantech.solalat.user.ui.main.medical.fragments

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
import com.raantech.solalat.user.databinding.FragmentMedicalServicesBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.main.adapters.medical.MedicalCategoriesRecyclerAdapter
import com.raantech.solalat.user.ui.main.medical.activities.MedicalsActivity
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicalServicesFragment : BaseBindingFragment<FragmentMedicalServicesBinding>(),
        BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_medical_services

    private val viewModel: MainViewModel by viewModels()
    lateinit var categoriesRecyclerAdapter: MedicalCategoriesRecyclerAdapter
    var refreshData: Boolean = false

    override fun onResume() {
        super.onResume()
        if (!refreshData) {
            refreshData = true
            return
        }
        categoriesRecyclerAdapter.clear()
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
        viewModel.getServicesCategories(ServiceTypesEnum.MEDICAL.value).observe(this, categoriesObserver())
    }

    private fun setUpRecyclerView() {
        categoriesRecyclerAdapter = MedicalCategoriesRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = categoriesRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
    }

    private fun hideShowNoData() {
        if (categoriesRecyclerAdapter.itemCount == 0) {
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
                            categoriesRecyclerAdapter.addItems(it)
                        }
                        hideShowNoData()
                    }

                    override fun onError(
                            subErrorCode: ResponseSubErrorsCodeEnum,
                            message: String,
                            errors: List<GeneralError>?
                    ) {
                        super.onError(subErrorCode, message, errors)
                        hideShowNoData()
                    }
                }, showError = false
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as ServiceCategory
        MedicalsActivity.start(requireContext(),item)
    }

}