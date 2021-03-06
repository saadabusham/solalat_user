package com.raantech.solalat.user.ui.main.barn.activities

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
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.databinding.ActivityBarnDetailsBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.dataview.viewimage.ViewImageActivity
import com.raantech.solalat.user.ui.main.adapters.barn.IndecatorImagesRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.barn.SelectedServicesRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.barn.SliderAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.openDial
import com.raantech.solalat.user.utils.extensions.openWhatsApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_favorite_toolbar.*
import kotlinx.android.synthetic.main.row_image_view.view.*

@AndroidEntryPoint
class BarnDetailsActivity : BaseBindingActivity<ActivityBarnDetailsBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()

    lateinit var indicatorRecyclerAdapter: IndecatorImagesRecyclerAdapter
    lateinit var onBoardingAdapter: SliderAdapter
    private var indicatorPosition = 0
    lateinit var servicesRecyclerAdapter: SelectedServicesRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_barn_details,
            hasToolbar = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.solalat,
            hasBackButton = true,
            showBackArrow = true
        )
        setUpBinding()
        setUpListeners()
        viewModel.getBarnDetails(intent.getIntExtra(Constants.BundleData.ID, 0))
            .observe(this, barnDetailsObserver())
    }

    private fun setData() {
        setUpRecyclerView()
        setUpPager()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.btnWhatsapp?.setOnClickListener {
            viewModel.barn.value?.contactNumber.openWhatsApp(this)
        }
        binding?.btnCallUs?.setOnClickListener {
            openDial(viewModel.barn.value?.contactNumber)
        }
        binding?.layoutToolbar?.imgFavorite?.setOnClickListener {
            if (viewModel.barn.value?.is_wishlist == true) {
                viewModel.barn.value?.id?.let { it1 ->
                    binding?.layoutToolbar?.isFavorite = false
                    viewModel.removeFromWishList(it1).observe(this, wishListActionObserver())
                }
            } else {
                viewModel.barn.value?.id?.let { it1 ->
                    binding?.layoutToolbar?.isFavorite = true
                    viewModel.addToWishList(it1).observe(this, wishListActionObserver())
                }
            }
        }
    }

    private fun barnDetailsObserver(): CustomObserverResponse<Barn> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Barn> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Barn?
                ) {
                    viewModel.barn.value = (data)
                    binding?.layoutToolbar?.isFavorite = data?.is_wishlist
                    setData()
                }
            }
        )
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
                    viewModel.barn.value?.is_wishlist = viewModel.barn.value?.is_wishlist != true
                }
            }, false
        )
    }

    private fun setUpRecyclerView() {
        servicesRecyclerAdapter = SelectedServicesRecyclerAdapter(this)
        binding?.rvServices?.adapter = servicesRecyclerAdapter
        viewModel.barn.value?.services?.let { servicesRecyclerAdapter.submitItems(it) }
    }

    private fun setUpPager() {
        onBoardingAdapter = SliderAdapter(this)
        binding?.vpOnBoarding?.adapter =
            onBoardingAdapter.apply {
                viewModel.barn.value?.additionalImages?.let {
                    viewModel.barn.value?.baseImage?.let { it1 -> submitItem(it1) }
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
        view?.imgMedia?.let { item.url?.let { it1 -> ViewImageActivity.start(this, it1, it) } }
    }

    companion object {
        fun start(
            context: Context?,
            barnId: Int
        ) {
            val intent = Intent(context, BarnDetailsActivity::class.java)
            intent.putExtra(Constants.BundleData.ID, barnId)
            context?.startActivity(intent)
        }

    }

}