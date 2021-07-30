package com.raantech.solalat.user.ui.main.medical.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.GeneralError
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.map.Address
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.databinding.ActivityMedicalsBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.main.adapters.medical.MedicalsRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.ui.map.MapActivity
import com.raantech.solalat.user.utils.displayLocationSettingsRequest
import com.raantech.solalat.user.utils.extensions.*
import com.raantech.solalat.user.utils.getLocationName
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.layout_favorite_toolbar.*
import permissions.dispatcher.NeedsPermission

@AndroidEntryPoint
class MedicalsActivity : BaseBindingActivity<ActivityMedicalsBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    var positionToUpdate: Int = -1
    lateinit var medicalsRecyclerAdapter: MedicalsRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.category =
            intent.getSerializableExtra(Constants.BundleData.CATEGORY) as ServiceCategory
        setContentView(
            layoutResID = R.layout.activity_medicals,
            hasToolbar = true,
            toolbarView = toolbar,
            hasTitle = true,
            titleString = viewModel.category?.name ?: resources.getString(R.string.solalat),
            hasBackButton = true,
            showBackArrow = true
        )
        setUpBinding()
        setUpListeners()
        setUpRecyclerView()
        init()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRecyclerView() {
        medicalsRecyclerAdapter = MedicalsRecyclerAdapter(this)
        binding?.recyclerView?.adapter = medicalsRecyclerAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && medicalsRecyclerAdapter.itemCount > 0 && !isFinished) {
                    loadData()
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

    private fun setUpListeners() {
//        binding?.layoutToolbar?.imgFavorite?.setOnClickListener {
//            if (viewModel.truck.value?.is_wishlist == true) {
//                viewModel.truck.value?.id?.let { it1 -> viewModel.removeFromWishList(it1).observe(this, wishListActionObserver()) }
//            } else {
//                viewModel.truck.value?.id?.let { it1 -> viewModel.addToWishList(it1).observe(this, wishListActionObserver()) }
//            }
//        }
        binding?.linearAddress?.setOnClickListener {
            MapActivity.start(this, resultLauncher)
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                onAddressUpdated(data?.getSerializableExtra(Constants.BundleData.ADDRESS) as Address)
            }
        }

    fun onAddressUpdated(address: Address) {
        viewModel.address = address
        binding?.tvAddress?.text =
            getLocationName(
                viewModel.address?.lat,
                viewModel.address?.lon
            )
        medicalsRecyclerAdapter.items.clear()
        loadData()
    }

    private fun loadData() {
        viewModel.getMedicals(
            medicalsRecyclerAdapter.itemCount,
            viewModel.category?.id,
            viewModel.address?.lat,
            viewModel.address?.lon
        ).observe(this, medicalsObserver())
    }


    private fun medicalsObserver(): CustomObserverResponse<List<Medical>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<Medical>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<Medical>>?
                ) {
                    isFinished = data?.body?.isNullOrEmpty() == true
                    data?.body?.let {
                        medicalsRecyclerAdapter.addItems(it)
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


    private fun hideShowNoData() {
        if (medicalsRecyclerAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.linearNoData?.visible()
        } else {
            binding?.layoutNoData?.linearNoData?.gone()
            binding?.recyclerView?.visible()
        }
    }

    private fun wishListActionObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<Any>?
                ) {
//                    medicalsRecyclerAdapter.items[positionToUpdate].isWishlist =
//                        medicalsRecyclerAdapter.items[positionToUpdate].isWishlist != true
//                    medicalsRecyclerAdapter.notifyItemChanged(positionToUpdate)
//                    positionToUpdate = -1
                }
            }, false
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Medical
        if (view?.id == R.id.btnCallNow)
            openDial(item.contactNumber)
        if (view?.id == R.id.btnLocation)
            navigateToLocation(item.latitude?.toDouble(), item.longitude?.toDouble())
        if (view?.id == R.id.imgFavorite) {
            positionToUpdate = position
            if (item.isWishlist == false) {
                viewModel.removeFromWishList(item.id ?: 0).observe(this, wishListActionObserver())
            } else {
                viewModel.addToWishList(item.id ?: 0).observe(this, wishListActionObserver())
            }
        }
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun init() {
        enableLocationServices()
    }

    private fun enableLocationServices() {
        if (!SmartLocation.with(this).location().state()
                .locationServicesEnabled()
        ) {
            displayLocationSettingsRequest(
                MapActivity.REQUEST_CODE_LOCATION
            )
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        requestLocationPermission(object : MyMultiPermissionListeners {
            override fun onPermissionGranted() {
                SmartLocation.with(this@MedicalsActivity)
                    .location()
                    .oneFix()
                    .start { location ->
                        onAddressUpdated(Address(location.latitude, location.longitude))
                    }
            }

            override fun onPermissionDenied() {
            }

        }, true)

    }

    companion object {
        fun start(
            context: Context?,
            category: ServiceCategory
        ) {
            val intent = Intent(context, MedicalsActivity::class.java)
            intent.putExtra(Constants.BundleData.CATEGORY, category)
            context?.startActivity(intent)
        }
    }

}