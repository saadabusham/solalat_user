package com.raantech.solalat.user.ui.addhorse.fragments

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.HorseAdsTypeEnum
import com.raantech.solalat.user.databinding.FragmentAddHorseStep2Binding
import com.raantech.solalat.user.ui.addhorse.adapter.GeneralStringDropDownAdapter
import com.raantech.solalat.user.ui.addhorse.viewmodel.AddHorseViewModel
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.utils.extensions.showValidationErrorAlert
import com.raantech.solalat.user.utils.extensions.validate
import com.raantech.solalat.user.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class AddHorseStep2Fragment : BaseBindingFragment<FragmentAddHorseStep2Binding>() {

    private val viewModel: AddHorseViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_add_horse_step2
    private lateinit var saleTypeSpinnerAdapter: GeneralStringDropDownAdapter
    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            hasBackButton = true,
            showBackArrow = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.add_horse,
            hasSubTitle = true,
            subTitle = R.string.add_price_and_contact_info
        )
        setUpBinding()
        setUpListeners()
        init()
    }

    private fun init() {
        setUpCategories()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        viewModel.selectedCountryCode.postValue(binding?.countryCodePicker?.defaultCountryCode)
        binding?.countryCodePicker?.setOnCountryChangeListener {
            viewModel.selectedCountryCode.postValue(it.phoneCode)
        }
        binding?.btnAddProduct?.setOnClickListener {
            if (isDataValid()) {
                viewModel.addHorse(
                    viewModel.getHorseRequest(
                        receivedWhatsapp = binding?.checkboxReceiveWhatsapp?.isChecked ?: false,
                        isActive = binding?.checkboxIsActive?.isChecked ?: false
                    )
                ).observe(this, addProductResultObserver())
            }
        }
    }

    private fun setUpCategories() {
        saleTypeSpinnerAdapter =
            GeneralStringDropDownAdapter(binding!!.spinnerSaleType, requireContext())
        saleTypeSpinnerAdapter.let { binding?.spinnerSaleType?.setSpinnerAdapter(it) }
        binding?.spinnerSaleType?.getSpinnerRecyclerView()?.layoutManager =
            LinearLayoutManager(activity)
        binding?.spinnerSaleType?.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            binding?.spinnerSaleType?.dismiss()
            if (newIndex == 0) {
                viewModel.horseAdsTypeEnum.postValue(HorseAdsTypeEnum.SELL)
                binding?.tvPriceTitle?.text = resources.getString(R.string.enter_price)
            } else {
                viewModel.horseAdsTypeEnum.postValue(HorseAdsTypeEnum.AUCTION)
                binding?.tvPriceTitle?.text =
                    resources.getString(R.string.enter_the_starting_price_of_the_auction)
            }
        }
        saleTypeSpinnerAdapter.setItems(
            arrayListOf(
                resources.getString(R.string.sale_direct),
                resources.getString(R.string.sale_auction)
            )
        )
        binding?.spinnerSaleType?.selectItemByIndex(0)
    }

    private fun addProductResultObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    navigationController.navigate(R.id.action_addHorseStep2Fragment_to_horseAddedSuccessFragment)
                }
            }, showError = true
        )
    }

    private fun isDataValid(): Boolean {
        binding?.edProductPrice?.text.toString().validate(
            ValidatorInputTypesEnums.DOUBLE,
            requireContext()
        )
            .let {
                if (!it.isValid) {
                    requireActivity().showValidationErrorAlert(
                        title = resources.getString(R.string.horse_price),
                        message = it.errorMessage
                    )
                    return false
                }
            }

        binding?.edPhoneNumber?.text.toString().validate(
            ValidatorInputTypesEnums.PHONE_NUMBER,
            requireContext()
        )
            .let {
                if (!it.isValid) {
                    requireActivity().showValidationErrorAlert(
                        title = resources.getString(R.string.phone_number),
                        message = it.errorMessage
                    )
                    return false
                }
            }

        return true
    }

}