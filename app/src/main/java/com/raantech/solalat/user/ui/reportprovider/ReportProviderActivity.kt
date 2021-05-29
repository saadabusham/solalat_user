package com.raantech.solalat.user.ui.reportprovider

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.ServiceCategoriesResponse
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.databinding.ActivityReportProviderBinding
import com.raantech.solalat.user.ui.addhorse.adapter.CategoriesSpinnerAdapter
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.utils.extensions.showErrorAlert
import com.raantech.solalat.user.utils.extensions.showValidationErrorAlert
import com.raantech.solalat.user.utils.extensions.validate
import com.raantech.solalat.user.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class ReportProviderActivity : BaseBindingActivity<ActivityReportProviderBinding>() {

    private val viewModel: ReportProviderViewModel by viewModels()

    private lateinit var categoriesSpinnerAdapter: CategoriesSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_report_provider,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.menu_report_provider
        )
        binding?.viewModel = viewModel
        setOnClickListeners()
        setUpCategories()
    }

    private fun setOnClickListeners() {
        binding?.btnSend?.setOnClickListener {
            if (isDataValid()) {

            }
        }
    }

    private fun sendReportResultObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {

                }
            })
    }

    private fun setUpCategories() {
        categoriesSpinnerAdapter =
            CategoriesSpinnerAdapter(binding!!.spinnerCategory, this)
        categoriesSpinnerAdapter.let { binding?.spinnerCategory?.setSpinnerAdapter(it) }
        binding?.spinnerCategory?.getSpinnerRecyclerView()?.layoutManager =
            LinearLayoutManager(this)
        binding?.spinnerCategory?.setOnSpinnerItemSelectedListener<ServiceCategory> { oldIndex, oldItem, newIndex, newItem ->
            binding?.spinnerCategory?.dismiss()
        }
        viewModel.getServicesCategories()
            .observe(this, categoriesResultObserver())
    }


    private fun categoriesResultObserver(): CustomObserverResponse<ServiceCategoriesResponse> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<ServiceCategoriesResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ServiceCategoriesResponse?
                ) {
                    data?.categories?.let {
                        categoriesSpinnerAdapter.setItems(it)
                        binding?.spinnerCategory?.selectItemByIndex(0)
                    }
                }
            })
    }

    private fun isDataValid(): Boolean {
        if (categoriesSpinnerAdapter.index == -1) {
            showErrorAlert(
                resources.getString(R.string.app_name),
                resources.getString(R.string.please_select_horse_type)
            )
            return false
        }
        binding?.edTitle?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            this
        )
            .let {
                if (!it.isValid) {
                    showValidationErrorAlert(
                        title = resources.getString(R.string.horse_name),
                        message = it.errorMessage
                    )
                    return false
                }
            }
        binding?.edSummery?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            this
        )
            .let {
                if (!it.isValid) {
                    showValidationErrorAlert(
                        title = resources.getString(R.string.father_name),
                        message = it.errorMessage
                    )
                    return false
                }
            }
        return true
    }

    companion object {

        fun start(context: Context?) {
            val intent = Intent(context, ReportProviderActivity::class.java)
            context?.startActivity(intent)
        }

    }

}