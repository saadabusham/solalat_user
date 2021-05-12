package com.raantech.solalat.user.ui.horse.fragments

import android.transition.TransitionManager
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.raantech.solalat.user.R
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
    private var lastPrice: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_horse_auction

    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            hasBackButton = true,
            showBackArrow = true,
            toolbarView = toolbar,
            hasTitle = true,
            titleString = viewModel.horse?.name,
            hasSubTitle = false
        )
        setUpBinding()
        setUpListeners()
        setUpPager()
        setUpRvPrice()
        viewModel.horse?.price?.amount = viewModel.horse?.price?.amount?.replace(".", "")
        lastPrice = viewModel.horse?.price?.amount
        viewModel.horse?.price?.amount?.let { refreshAuction() }
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
        binding?.layoutAuctionTimer?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.btnAddPrice?.setOnClickListener {

        }
    }

    private fun setUpRvPrice() {
        priceDigitsRecyclerAdapter = PriceDigitsRecyclerAdapter(requireContext())
        binding?.rvPrice?.adapter = priceDigitsRecyclerAdapter
        binding?.rvPrice?.setOnItemClickListener(object :
            BaseBindingRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, item: Any) {
                item as Int
                val doublePrice = position == 2
                if (view?.id == R.id.imgUp) {
                    var numberToPlus = StringBuilder()
                    for (i in 0..position)
                        numberToPlus.append(if (i == 0) 1 else 0)
                    lastPrice =
                        (lastPrice?.toInt()?.plus(
                            if (doublePrice) numberToPlus.toString().toInt() * 2
                            else numberToPlus.toString().toInt()
                        )).toString()
                    refreshAuction()
                } else {
                    priceDigitsRecyclerAdapter.items.reversed().joinToString(separator = "")
                        .toInt().let {
                            if (it > 200 &&
                                it > viewModel.horse?.price?.amount?.toInt()?.plus(200) ?: 0
                            ) {
                                var numberToMinus = StringBuilder()
                                for (i in 0..position)
                                    numberToMinus.append(if (i == 0) 1 else 0)
                                lastPrice?.toInt()?.minus(
                                    if (doublePrice) numberToMinus.toString().toInt() * 2
                                    else numberToMinus.toString().toInt()
                                )?.let {
                                    if (it > viewModel.horse?.price?.amount?.toInt() ?: 0) {
                                        lastPrice = it.toString()
                                        refreshAuction()
                                    }
                                }
                            }
                        }
                }
            }
        })
    }

    private fun refreshAuction() {
        val list = mutableListOf<Int>()
        lastPrice?.reversed()?.forEach {
            list.add(Integer.parseInt(it.toString()))
        }
        TransitionManager.beginDelayedTransition(binding?.rvPrice)
        priceDigitsRecyclerAdapter.items.clear()
        priceDigitsRecyclerAdapter.submitItems(list)
    }

    private fun setUpPager() {
        onBoardingAdapter = SliderAdapter(requireContext())
        binding?.vpOnBoarding?.adapter =
            onBoardingAdapter.apply {
                viewModel.horse?.additionalImages?.let {
                    viewModel.horse?.baseImage?.let { it1 -> submitItem(it1) }
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

}