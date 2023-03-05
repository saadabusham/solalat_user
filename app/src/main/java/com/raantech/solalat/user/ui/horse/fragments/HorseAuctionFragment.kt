package com.raantech.solalat.user.ui.horse.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager2.widget.ViewPager2
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.NotificationsTypeEnum
import com.raantech.solalat.user.data.models.horses.HorseDetails
import com.raantech.solalat.user.data.models.horses.horsesubscription.IncreaseResponse
import com.raantech.solalat.user.data.models.horses.horsesubscription.MinimumBid
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.databinding.FragmentHorseAuctionBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.dataview.viewimage.ViewImageActivity
import com.raantech.solalat.user.ui.horse.adapters.PriceDigitsRecyclerAdapter
import com.raantech.solalat.user.ui.horse.viewmodels.HorseViewModel
import com.raantech.solalat.user.ui.main.adapters.barn.IndecatorImagesRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.barn.SliderAdapter
import com.raantech.solalat.user.utils.extensions.getSerializableData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.row_image_view.view.*

@AndroidEntryPoint
class HorseAuctionFragment : BaseBindingFragment<FragmentHorseAuctionBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: HorseViewModel by activityViewModels()
    lateinit var indicatorRecyclerAdapter: IndecatorImagesRecyclerAdapter
    lateinit var onBoardingAdapter: SliderAdapter
    private var indicatorPosition = 0
    private lateinit var priceDigitsRecyclerAdapter: PriceDigitsRecyclerAdapter
    override fun getLayoutId(): Int = R.layout.fragment_horse_auction

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            newHorseBidPassingReceiver, IntentFilter(
                NotificationsTypeEnum.NEW_HORSE_AUCTION_BID.value
            )
        )
        viewModel.horseId?.let { viewModel.getHorse(it).observe(this, horseObserver()) }
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(newHorseBidPassingReceiver)
    }

    override fun onViewVisible() {
        super.onViewVisible()
    }

    private fun init() {
        addToolbar(
            hasToolbar = true,
            hasBackButton = true,
            showBackArrow = true,
            toolbarView = toolbar,
            hasTitle = true,
            titleString = viewModel.horse.value?.name,
            hasSubTitle = false
        )
        binding?.layoutToolbar?.isFavorite = viewModel.horse.value?.is_wishlist
        setUpBinding()
        setUpListeners()
        setUpPager()
//        setUpRvPrice()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
        binding?.layoutAuctionTimer?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.btnAddPrice?.setOnClickListener {
            viewModel.horse.value?.id?.let { it1 ->
                viewModel.increaseAuctionSubscription(it1)
                    .observe(this, increaseAuctionResultObserver())
            }
        }
        binding?.btnLeaveAuction?.setOnClickListener {
            viewModel.horse.value?.id?.let { it1 ->
                viewModel.cancelAuctionSubscription(it1)
                    .observe(this, cancelHorseSubscriptionObserver())
            }
        }
        binding?.layoutToolbar?.imgFavorite?.setOnClickListener {
            if (viewModel.horse.value?.is_wishlist == true) {
                viewModel.horse.value?.id?.let { it1 ->
                    binding?.layoutToolbar?.isFavorite = false
                    viewModel.removeFromWishList(it1).observe(this, wishListActionObserver())
                }
            } else {
                viewModel.horse.value?.id?.let { it1 ->
                    binding?.layoutToolbar?.isFavorite = true
                    viewModel.addToWishList(it1).observe(this, wishListActionObserver())
                }
            }
        }
    }

    private fun setUpRvPrice() {
        priceDigitsRecyclerAdapter = PriceDigitsRecyclerAdapter(requireContext())
        binding?.rvPrice?.adapter = priceDigitsRecyclerAdapter
//        binding?.rvPrice?.itemAnimator = null
        binding?.rvPrice?.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                item as Int
                val doublePrice = position == 2
                if (view?.id == R.id.imgUp) {
                    var numberToPlus = StringBuilder()
                    for (i in 0..position)
                        numberToPlus.append(if (i == 0) 1 else 0)
                    ((viewModel.latestPrice.value ?: 0.0).plus(
                        if (doublePrice) numberToPlus.toString().toInt() * 2
                        else numberToPlus.toString().toInt()
                    )).let {
                        refreshAuction(it)
                    }
                } else {
                    priceDigitsRecyclerAdapter.items.reversed().joinToString(separator = "")
                        .toInt().let {
                            if (it > (viewModel.horseExtraData.value?.minimumBid?.amount?.toDoubleOrNull()
                                    ?: 200.0) &&
                                it > (viewModel.horse.value?.price?.amount?.toInt()?.plus(
                                    viewModel.horseExtraData.value?.minimumBid?.amount?.toDoubleOrNull()
                                        ?: 200.0
                                ) ?: 0.0)
                            ) {
                                var numberToMinus = StringBuilder()
                                for (i in 0..position)
                                    numberToMinus.append(if (i == 0) 1 else 0)
                                viewModel.latestPrice.value?.minus(
                                    if (doublePrice) numberToMinus.toString().toInt() * 2
                                    else numberToMinus.toString().toInt()
                                )?.let {
                                    if (it > (viewModel.horse.value?.price?.amount?.toInt() ?: 0)) {
                                        refreshAuction(it)
                                    }
                                }
                            }
                        }
                }
            }
        })
    }

    private fun refreshAuction(latestPrice: Double) {
//        val list = mutableListOf<Int>()
//        viewModel.latestPrice.value?.toString()?.reversed()?.forEach {
//            list.add(Integer.parseInt(it.toString()))
//        }
//        TransitionManager.beginDelayedTransition(binding?.rvPrice)
//        priceDigitsRecyclerAdapter.items.clear()
//        priceDigitsRecyclerAdapter.submitItems(list)
        viewModel.latestPrice.value = latestPrice
    }

    private fun setUpPager() {
        onBoardingAdapter = SliderAdapter(requireContext())
        binding?.vpOnBoarding?.adapter =
            onBoardingAdapter.apply {
                viewModel.horse.value?.additionalImages?.let {
                    viewModel.horse.value?.baseImage?.let { it1 -> submitItem(it1) }
                    submitItems(it)
                }
            }
        binding?.vpOnBoarding?.setOnItemClickListener(this)
        setUpIndicator()
    }

    private fun setUpIndicator() {
        indicatorRecyclerAdapter = IndecatorImagesRecyclerAdapter(requireContext())
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

    private fun wishListActionObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<Any>?
                ) {
                    viewModel.horse.value?.is_wishlist = viewModel.horse.value?.is_wishlist != true
                }
            }, false
        )
    }

    private fun horseObserver(): CustomObserverResponse<HorseDetails> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<HorseDetails> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: HorseDetails?
                ) {
                    viewModel.horse.value = data?.horse
                    viewModel.horseExtraData.value = data?.extraData
                    init()
                    (viewModel.horseExtraData.value?.maxPrice?.amount
                        ?: viewModel.horse.value?.price?.amount)?.let {
//                            viewModel.latestPrice.value = it.toDoubleOrNull() ?: 0.0
                        refreshAuction(it.toDoubleOrNull() ?: 0.0)
                    }
                }
            }, true
        )
    }

    private fun cancelHorseSubscriptionObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    requireActivity().onBackPressed()
                }
            }
        )
    }

    private fun increaseAuctionResultObserver(): CustomObserverResponse<IncreaseResponse> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<IncreaseResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: IncreaseResponse?
                ) {
//                    refreshAuction(
//                        data?.minimumBid?.newPrice?.amount?.toDoubleOrNull() ?: 0.0
//                    )
//                    viewModel.horseExtraData.value = viewModel.horseExtraData.value?.apply {
//                        this.maxPrice = data?.minimumBid?.newPrice
//                    }
                }
            }
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Media
        view?.imgMedia?.let {
            item.url?.let { it1 ->
                ViewImageActivity.start(
                    requireActivity(),
                    it1,
                    it
                )
            }
        }
    }

    private val newHorseBidPassingReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                try {
                    if (bundle.containsKey("data")) {
                        bundle.getSerializableData("data",MinimumBid::class.java)?.let {
                            refreshAuction(
                                it.newPrice?.amount?.toDoubleOrNull() ?: 0.0
                            )
                            viewModel.horseExtraData.value = viewModel.horseExtraData.value?.apply {
                                this.maxPrice = it.newPrice
                            }
                        }
                    }
                } catch (e: Exception) {

                }
            }
        }
    }
}