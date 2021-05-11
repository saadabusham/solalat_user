package com.raantech.solalat.user.ui.main.truck.fragments

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.GeneralError
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.City
import com.raantech.solalat.user.data.models.map.Address
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.databinding.FragmentTrucksBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.main.adapters.truck.TrucksGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.dialogs.CitiesBottomSheet
import com.raantech.solalat.user.ui.main.truck.activities.TruckDetailsActivity
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.visible
import com.raantech.solalat.user.utils.getLocationName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrucksFragment : BaseBindingFragment<FragmentTrucksBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()

    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    lateinit var trucksGridRecyclerAdapter: TrucksGridRecyclerAdapter

    var refreshData: Boolean = false

    override fun onResume() {
        super.onResume()
        if (!refreshData) {
            refreshData = true
            return
        }
        trucksGridRecyclerAdapter.clear()
        loadBarns()
    }

    override fun getLayoutId(): Int = R.layout.fragment_trucks

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
        init()
    }

    private fun init() {
        setUpRecyclerView()
        loadBarns()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.cvFromCity?.setOnClickListener {
            CitiesBottomSheet(object : CitiesBottomSheet.CityPickerCallBack {
                override fun callBack(selectedCities: List<City>) {
                    viewModel.fromCity = selectedCities[0]
                    binding?.tvFromCity?.text = selectedCities.map { it.name }.joinToString()
                    trucksGridRecyclerAdapter.items.clear()
                    loadBarns()
                }
            }, viewModel, viewModel.cities,true).show(childFragmentManager, "CitiesPicker")
        }
        binding?.cvToCity?.setOnClickListener {
            CitiesBottomSheet(object : CitiesBottomSheet.CityPickerCallBack {
                override fun callBack(selectedCities: List<City>) {
                    viewModel.toCity = selectedCities[0]
                    binding?.tvToCity?.text = selectedCities.map { it.name }.joinToString()
                    trucksGridRecyclerAdapter.items.clear()
                    loadBarns()
                }
            }, viewModel, viewModel.cities,true).show(childFragmentManager, "CitiesPicker")
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.address =
                    data?.getSerializableExtra(Constants.BundleData.ADDRESS) as Address
                binding?.tvFromCity?.text =
                    getLocationName(
                        viewModel.address?.lat,
                        viewModel.address?.lon
                    )
                trucksGridRecyclerAdapter.items.clear()
                loadBarns()
            }
        }

    private fun loadBarns() {
        viewModel.getTrucks(
            trucksGridRecyclerAdapter.itemCount,
            viewModel.fromCity?.id,
            viewModel.toCity?.id,
            viewModel.address?.lat,
            viewModel.address?.lon
        ).observe(this, truckObserver())
    }

    private fun setUpRecyclerView() {
        trucksGridRecyclerAdapter = TrucksGridRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = trucksGridRecyclerAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && trucksGridRecyclerAdapter.itemCount > 0 && !isFinished) {
                    loadBarns()
                }
            }

            override fun isLoading(): Boolean {
                return loading.value ?: false
            }

            override fun hasLoadedAllItems(): Boolean {
                return isFinished
            }

        })
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(object : LoadingListItemCreator {
                override fun onCreateViewHolder(
                    parent: ViewGroup?,
                    viewType: Int
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.loading_row, parent, false)
                    return object : RecyclerView.ViewHolder(view) {}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

                }

            })
            .build()

    }

    private fun hideShowNoData() {
        if (trucksGridRecyclerAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.linearNoData?.visible()
        } else {
            binding?.layoutNoData?.linearNoData?.gone()
            binding?.recyclerView?.visible()
        }
    }

    private fun observeLoading() {
        loading.observe(this, Observer {
            if (it) {
                binding?.recyclerView?.gone()
//                binding?.layoutShimmer?.shimmerViewContainer?.visible()
//                binding?.layoutShimmer?.shimmerViewContainer?.startShimmer()
            } else {
//                binding?.layoutShimmer?.shimmerViewContainer?.gone()
//                binding?.layoutShimmer?.shimmerViewContainer?.stopShimmer()
                binding?.recyclerView?.visible()
            }
        })
    }

    private fun truckObserver(): CustomObserverResponse<List<Truck>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Truck>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<Truck>>?
                ) {
                    isFinished = data?.body?.isNullOrEmpty() == true
                    data?.body?.let {
                        trucksGridRecyclerAdapter.addItems(it)
                    }
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onLoading() {
                    loading.postValue(true)
                }
            }, false, showError = false
        )
    }


    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Truck
        TruckDetailsActivity.start(requireContext(),item)
    }


}