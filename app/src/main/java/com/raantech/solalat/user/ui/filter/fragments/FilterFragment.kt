package com.raantech.solalat.user.ui.filter.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.GeneralError
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.HorseAdsTypeEnum
import com.raantech.solalat.user.data.enums.ServiceTypesEnum
import com.raantech.solalat.user.data.models.GeneralLookup
import com.raantech.solalat.user.data.models.Price
import com.raantech.solalat.user.data.models.ServiceCategoriesResponse
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.databinding.FragmentFilterBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.base.views.appseekbar.AppSeekBar
import com.raantech.solalat.user.ui.base.views.appseekbar.OnRangeChangedListener
import com.raantech.solalat.user.ui.filter.adapters.GeneralFilterRecyclerAdapter
import com.raantech.solalat.user.ui.filter.viewmodels.FiltersViewModel
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.setupClearButtonWithAction
import com.raantech.solalat.user.utils.extensions.visible
import com.raantech.solalat.user.utils.plus
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FilterFragment : BaseBindingFragment<FragmentFilterBinding>() {

    private val viewModel: FiltersViewModel by activityViewModels()


    lateinit var categoryAdapter: GeneralFilterRecyclerAdapter
    lateinit var subCategoryAdapter: GeneralFilterRecyclerAdapter
    lateinit var sellTypeAdapter: GeneralFilterRecyclerAdapter

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
        setUpRvSubCategory()
        setUpRvCategory()
        setUpRvSellType()
        initSearch()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.seekbarPrice?.progressLeft = 0
        binding?.seekbarPrice?.setProgress(0f, 10000f)
        binding?.seekbarPrice?.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(
                view: AppSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                viewModel.minPrice = leftValue.toDouble()
                viewModel.maxPrice = rightValue.toDouble()
            }

            override fun onStartTrackingTouch(view: AppSeekBar?, isLeft: Boolean) {

            }

            override fun onStopTrackingTouch(
                view: AppSeekBar?,
                isLeft: Boolean,
                condition: Price?
            ) {
                applySearch()
            }

        })
        binding?.btnShow?.setOnClickListener {
            if (viewModel.searchResults.value.equals("0"))
                return@setOnClickListener
            handelShowResults()
        }
    }

    private fun handelShowResults() {
        when (viewModel.selectedCategory?.type) {
            ServiceTypesEnum.HORSES.value -> {
                navigationController.navigate(R.id.action_filterFragment_to_filteredHorsesFragment)
            }
            ServiceTypesEnum.ACCESSORIES.value -> {
                navigationController.navigate(R.id.action_filterFragment_to_filteredAccessoriesFragment)
            }
            ServiceTypesEnum.MEDICAL.value -> {
                navigationController.navigate(R.id.action_filterFragment_to_filteredMedicalsFragment)
            }
            ServiceTypesEnum.TRANSPORTATION.value -> {
                navigationController.navigate(R.id.action_filterFragment_to_filteredTrucksFragment)
            }
            else -> {
                navigationController.navigate(R.id.action_filterFragment_to_filteredBarnFragment)
            }
        }
    }

    private fun initSearch() {
        binding?.edTitle?.setupClearButtonWithAction()
        viewModel.compositeDisposable + binding?.edTitle?.textChangeEvents()
            ?.skipInitialValue()
            ?.debounce(600, TimeUnit.MILLISECONDS)
            ?.distinctUntilChanged()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe {
                viewModel.searchText.value = it.text.toString()
                applySearch()
            }
    }

    private fun setUpRvCategory() {
        categoryAdapter = GeneralFilterRecyclerAdapter(requireActivity())
        binding?.rvCategory?.adapter = categoryAdapter
        binding?.rvCategory?.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                item as GeneralLookup
                viewModel.selectedCategory = item
                updateCategoryInfo(item)
            }
        })
        categoryAdapter.submitItems(
            arrayListOf(
                GeneralLookup(
                    type = ServiceTypesEnum.HORSES.value,
                    value = resources.getString(R.string.horses),
                    selected = MutableLiveData(true)
                ), GeneralLookup(
                    type = ServiceTypesEnum.ACCESSORIES.value,
                    value = resources.getString(R.string.accessories)
                ), GeneralLookup(
                    type = ServiceTypesEnum.MEDICAL.value,
                    value = resources.getString(R.string.medical)
                ), GeneralLookup(
                    type = ServiceTypesEnum.TRANSPORTATION.value,
                    value = resources.getString(R.string.truck)
                ), GeneralLookup(
                    type = ServiceTypesEnum.BARN.value,
                    value = resources.getString(R.string.stable)
                )
            )
        )
        categoryAdapter.items[0].let {
            viewModel.selectedCategory = it
            viewModel.getServicesCategories(it.type ?: ServiceTypesEnum.HORSES.value)
                .observe(this@FilterFragment, subCategoriesResultObserver())
        }
    }

    private fun setUpRvSubCategory() {
        subCategoryAdapter = GeneralFilterRecyclerAdapter(requireActivity())
        binding?.rvSubCategory?.adapter = subCategoryAdapter
        binding?.rvSubCategory?.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                viewModel.selectedSubCategory = item as GeneralLookup
                applySearch()
            }
        })
    }

    private fun setUpRvSellType() {
        sellTypeAdapter = GeneralFilterRecyclerAdapter(requireActivity())
        binding?.rvSellType?.adapter = sellTypeAdapter
        binding?.rvSellType?.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                viewModel.typeOfSale = (item as GeneralLookup).type
                applySearch()
            }
        })
        sellTypeAdapter.submitItems(
            arrayListOf(
                GeneralLookup(
                    type = HorseAdsTypeEnum.SELL.value,
                    value = resources.getString(R.string.sale_direct),
                    selected = MutableLiveData(true)
                ), GeneralLookup(
                    type = HorseAdsTypeEnum.AUCTION.value,
                    value = resources.getString(R.string.sale_auction)
                )
            )
        )
        viewModel.typeOfSale = sellTypeAdapter.items[0].type
    }

    private fun subCategoriesResultObserver(): CustomObserverResponse<ServiceCategoriesResponse> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<ServiceCategoriesResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ServiceCategoriesResponse?
                ) {
                    subCategoryAdapter.clear()
                    subCategoryAdapter.selectedPosition = -1
                    data?.categories?.let {
                        if (it.isNotEmpty()) {
                            hideShowSubCategory(false)
                            subCategoryAdapter.submitItems(it.map {
                                GeneralLookup(
                                    id = it.id,
                                    value = it.name
                                )
                            })
                            subCategoryAdapter.selectedPosition = 0
                            subCategoryAdapter.items[0].selected.postValue(true)
                            viewModel.selectedSubCategory = subCategoryAdapter.items[0]
                            applySearch()
                        } else
                            hideShowSubCategory(true)
                    } ?: also {
                        hideShowSubCategory(true)
                    }
                }
            })
    }

    private fun hideShowSubCategory(hide: Boolean) {
        if (hide) {
            binding?.rvSubCategory?.gone()
            binding?.tvSubCategory?.gone()
        } else {
            binding?.rvSubCategory?.visible()
            binding?.tvSubCategory?.visible()
        }
    }

    private fun updateCategoryInfo(category: GeneralLookup) {
        viewModel.selectedSubCategory = null
        binding?.tvSubCategory?.setText(
            when (category.type) {
                ServiceTypesEnum.HORSES.value -> {
                    setResultCount(0)
                    hideShowSellType(false)
                    hideShowPrice(false)
                    hideShowSubCategory(false)
                    category.type.let {
                        viewModel.getServicesCategories(it)
                            .observe(this@FilterFragment, subCategoriesResultObserver())
                    }
                    R.string.horse_type
                }
                ServiceTypesEnum.ACCESSORIES.value -> {
                    setResultCount(0)
                    hideShowSellType(true)
                    hideShowPrice(false)
                    hideShowSubCategory(false)
                    category.type.let {
                        viewModel.getServicesCategories(it)
                            .observe(this@FilterFragment, subCategoriesResultObserver())
                    }
                    R.string.accessories_type
                }
                ServiceTypesEnum.MEDICAL.value -> {
                    setResultCount(0)
                    hideShowSellType(true)
                    hideShowPrice(true)
                    hideShowSubCategory(false)
                    category.type.let {
                        viewModel.getServicesCategories(it)
                            .observe(this@FilterFragment, subCategoriesResultObserver())
                    }
                    R.string.medical_type
                }
                ServiceTypesEnum.TRANSPORTATION.value -> {
                    setResultCount(0)
                    hideShowSellType(true)
                    hideShowPrice(true)
                    hideShowSubCategory(true)
                    applySearch()
                    R.string.truck_type
                }
                else -> {
                    setResultCount(0)
                    hideShowSellType(true)
                    hideShowPrice(false)
                    hideShowSubCategory(true)
                    applySearch()
                    R.string.stable_type
                }
            }
        )

    }

    private fun hideShowSellType(hide: Boolean) {
        if (hide) {
            binding?.tvSellType?.gone()
            binding?.rvSellType?.gone()
        } else {
            binding?.tvSellType?.visible()
            binding?.rvSellType?.visible()
        }
    }

    private fun hideShowPrice(hide: Boolean) {
        if (hide) {
            binding?.tvPrice?.gone()
            binding?.relPrice?.gone()
        } else {
            binding?.tvPrice?.visible()
            binding?.relPrice?.visible()
        }
    }

    private fun applySearch() {
        when (viewModel.selectedCategory?.type) {
            ServiceTypesEnum.BARN.value -> {
                viewModel.filterStable().observe(this, stableResultObserver())
            }
            ServiceTypesEnum.ACCESSORIES.value -> {
                viewModel.filterAccessories().observe(this, accessoriesResultObserver())
            }
            ServiceTypesEnum.MEDICAL.value -> {
                viewModel.filterMedical().observe(this, medicalResultObserver())
            }
            ServiceTypesEnum.TRANSPORTATION.value -> {
                viewModel.filterTruck().observe(this, truckResultObserver())
            }
            else -> {
                viewModel.filterHorses().observe(this, horsesResultObserver())
            }
        }
    }

    private fun horsesResultObserver(): CustomObserverResponse<List<Horse>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Horse>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Horse>?
                ) {
                    setResultCount(data?.size ?: 0)
                    viewModel.horsesResults.apply {
                        clear()
                        data?.let { addAll(it) }
                    }
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    setResultCount(0)
                }
            })
    }

    private fun accessoriesResultObserver(): CustomObserverResponse<List<Accessory>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Accessory>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Accessory>?
                ) {
                    setResultCount(data?.size ?: 0)
                    viewModel.accessoriesResults.apply {
                        clear()
                        data?.let { addAll(it) }
                    }
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    setResultCount(0)
                }
            })
    }

    private fun medicalResultObserver(): CustomObserverResponse<List<Medical>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Medical>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Medical>?
                ) {
                    setResultCount(data?.size ?: 0)
                    viewModel.medicalResults.apply {
                        clear()
                        data?.let { addAll(it) }
                    }
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    setResultCount(0)
                }
            })
    }

    private fun truckResultObserver(): CustomObserverResponse<List<Truck>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Truck>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Truck>?
                ) {
                    setResultCount(data?.size ?: 0)
                    viewModel.truckResults.apply {
                        clear()
                        data?.let { addAll(it) }
                    }
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    setResultCount(0)
                }
            })
    }

    private fun stableResultObserver(): CustomObserverResponse<List<Barn>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Barn>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Barn>?
                ) {
                    setResultCount(data?.size ?: 0)
                    viewModel.stableResults.apply {
                        clear()
                        data?.let { addAll(it) }
                    }
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    setResultCount(0)
                }
            })
    }

    private fun setResultCount(count: Int) {
        viewModel.searchResults.postValue(count.toString())
    }
}