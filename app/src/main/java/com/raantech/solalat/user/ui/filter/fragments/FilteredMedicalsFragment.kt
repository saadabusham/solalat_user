package com.raantech.solalat.user.ui.filter.fragments

import android.Manifest
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.map.Address
import com.raantech.solalat.user.data.models.medical.Medical
import com.raantech.solalat.user.databinding.FragmentFilteredMedicalsBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.filter.viewmodels.FiltersViewModel
import com.raantech.solalat.user.ui.main.adapters.medical.MedicalsRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.ui.map.MapActivity
import com.raantech.solalat.user.utils.displayLocationSettingsRequest
import com.raantech.solalat.user.utils.extensions.MyMultiPermissionListeners
import com.raantech.solalat.user.utils.extensions.navigateToLocation
import com.raantech.solalat.user.utils.extensions.openDial
import com.raantech.solalat.user.utils.extensions.requestLocationPermission
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.layout_toolbar.*
import permissions.dispatcher.NeedsPermission

@AndroidEntryPoint
class FilteredMedicalsFragment : BaseBindingFragment<FragmentFilteredMedicalsBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_filtered_medicals
    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: FiltersViewModel by activityViewModels()
    lateinit var medicalsRecyclerAdapter: MedicalsRecyclerAdapter

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
        medicalsRecyclerAdapter = MedicalsRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = medicalsRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
        medicalsRecyclerAdapter.submitItems(viewModel.medicalResults)
    }

    private fun wishListActionObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<Any>?
                ) {
                }
            }, false
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Medical
        if (view?.id == R.id.btnCallNow)
            requireContext().openDial(item.contactNumber)
        if (view?.id == R.id.btnLocation)
            requireActivity().navigateToLocation(
                item.latitude?.toDouble(),
                item.longitude?.toDouble()
            )
        if (view?.id == R.id.imgFavorite) {
            if (item.isWishlist == true) {
                mainViewModel.removeFromWishList(item.id ?: 0)
                    .observe(this, wishListActionObserver())
            } else {
                mainViewModel.addToWishList(item.id ?: 0).observe(this, wishListActionObserver())
            }
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun init() {
        enableLocationServices()
    }

    private fun enableLocationServices() {
        if (!SmartLocation.with(requireContext()).location().state()
                .locationServicesEnabled()
        ) {
            displayLocationSettingsRequest(
                requireActivity(),
                MapActivity.REQUEST_CODE_LOCATION
            )
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        requireActivity().requestLocationPermission(object : MyMultiPermissionListeners {
            override fun onPermissionGranted() {
                SmartLocation.with(requireContext())
                    .location()
                    .oneFix()
                    .start { location ->
//                        onAddressUpdated(Address(location.latitude, location.longitude))
                    }
            }

            override fun onPermissionDenied() {
            }

        }, true)

    }


}