package com.raantech.solalat.user.ui.main.truck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.DataView
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.data.models.truck.Truck
import com.raantech.solalat.user.databinding.ActivityTruckDetailsBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.dataview.viewimage.ViewImageActivity
import com.raantech.solalat.user.ui.main.adapters.DataViewRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.barn.IndecatorImagesRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.barn.SliderAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.openDial
import com.raantech.solalat.user.utils.extensions.openWhatsApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_favorite_toolbar.*
import kotlinx.android.synthetic.main.row_image_view.view.*

@AndroidEntryPoint
class TruckDetailsActivity : BaseBindingActivity<ActivityTruckDetailsBinding>(),
        BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()

    lateinit var indicatorRecyclerAdapter: IndecatorImagesRecyclerAdapter
    lateinit var onBoardingAdapter: SliderAdapter
    private var indicatorPosition = 0
    lateinit var dataViewAdapter: DataViewRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.truck.value = (intent.getSerializableExtra(Constants.BundleData.TRUCK) as Truck)
        setContentView(
                layoutResID = R.layout.activity_truck_details,
                hasToolbar = true,
                toolbarView = toolbar,
                hasTitle = true,
                title = R.string.solalat,
                hasBackButton = true,
                showBackArrow = true
        )
        binding?.layoutToolbar?.isFavorite = viewModel.truck.value?.is_wishlist
        setUpBinding()
        setUpListeners()
        setUpRecyclerView()
        init()
    }

    private fun setUpRecyclerView() {
        dataViewAdapter = DataViewRecyclerAdapter(this)
        binding?.rvDataView?.adapter = dataViewAdapter
        viewModel.truck.value?.let {
            dataViewAdapter.submitItems(
                    arrayListOf(
                            DataView(resources.getString(R.string.truck_owner_name), it.name),
                            DataView(resources.getString(R.string.truck_type), it.name),
                            DataView(resources.getString(R.string.manufacturingYear), it.manufacturingYear),
                            DataView(resources.getString(R.string.plate_number), it.truckNumber),
                            DataView(resources.getString(R.string.provided_cities), it.cities?.map { it.name }?.joinToString())
                    )
            )
        }
    }

    private fun init() {
        setUpPager()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.btnWhatsapp?.setOnClickListener {
            viewModel.truck.value?.contactNumber.openWhatsApp(this)
        }
        binding?.btnCallUs?.setOnClickListener {
            openDial(viewModel.truck.value?.contactNumber)
        }
        binding?.layoutToolbar?.imgFavorite?.setOnClickListener {
            if (viewModel.truck.value?.is_wishlist == true) {
                viewModel.truck.value?.id?.let { it1 -> viewModel.removeFromWishList(it1).observe(this, wishListActionObserver()) }
            } else {
                viewModel.truck.value?.id?.let { it1 -> viewModel.addToWishList(it1).observe(this, wishListActionObserver()) }
            }
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
                        viewModel.truck.value?.is_wishlist = viewModel.truck.value?.is_wishlist != true
                        binding?.layoutToolbar?.isFavorite = viewModel.truck.value?.is_wishlist
                    }
                }, true
        )
    }

    private fun setUpPager() {
        onBoardingAdapter = SliderAdapter(this)
        binding?.vpOnBoarding?.adapter =
                onBoardingAdapter.apply {
                    viewModel.truck.value?.additionalImages?.let {
                        viewModel.truck.value?.baseImage?.let { it1 -> submitItem(it1) }
                        submitItems(it)
                    }
                }
        binding?.vpOnBoarding?.setOnItemClickListener(this)
        setUpIndicator()
    }

    private fun setUpIndicator() {
        indicatorRecyclerAdapter = IndecatorImagesRecyclerAdapter(this)
        binding?.recyclerViewImagesDot?.adapter = indicatorRecyclerAdapter
        onBoardingAdapter.items.let {
            it.withIndex().forEach {
                indicatorRecyclerAdapter.submitItem(it.index == 0)
            }
        }
        binding?.vpOnBoarding?.registerOnPageChangeCallback(
                pagerCallback
        )
    }

    private var pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateIndicator(position)
        }
    }

    private fun updateIndicator(position: Int) {
        indicatorRecyclerAdapter.items[indicatorPosition] = false
        indicatorRecyclerAdapter.items[position] = true
        indicatorRecyclerAdapter.notifyDataSetChanged()
        indicatorPosition = position
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Media
        view?.imgMedia?.let { ViewImageActivity.start(this, item.url, it) }
    }

    companion object {
        fun start(context: Context?,
                  truck: Truck) {
            val intent = Intent(context, TruckDetailsActivity::class.java)
            intent.putExtra(Constants.BundleData.TRUCK, truck)
            context?.startActivity(intent)
        }

    }

}