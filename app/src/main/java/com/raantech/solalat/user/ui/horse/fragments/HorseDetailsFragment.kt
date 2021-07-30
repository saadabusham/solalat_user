package com.raantech.solalat.user.ui.horse.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.HorseAdsTypeEnum
import com.raantech.solalat.user.data.models.DataView
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.databinding.FragmentHorseDetailsBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.dataview.viewimage.ViewImageActivity
import com.raantech.solalat.user.ui.horse.viewmodels.HorseViewModel
import com.raantech.solalat.user.ui.main.adapters.DataViewRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.barn.IndecatorImagesRecyclerAdapter
import com.raantech.solalat.user.ui.main.adapters.barn.SliderAdapter
import com.raantech.solalat.user.utils.extensions.openDial
import com.raantech.solalat.user.utils.extensions.openWhatsApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.row_image_view.view.*

@AndroidEntryPoint
class HorseDetailsFragment : BaseBindingFragment<FragmentHorseDetailsBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: HorseViewModel by activityViewModels()
    lateinit var indicatorRecyclerAdapter: IndecatorImagesRecyclerAdapter
    lateinit var onBoardingAdapter: SliderAdapter
    private var indicatorPosition = 0
    lateinit var dataViewAdapter: DataViewRecyclerAdapter

    override fun getLayoutId(): Int = R.layout.fragment_horse_details

    override fun onViewVisible() {
        super.onViewVisible()
        viewModel.horseId?.let { viewModel.getHorse(it).observe(this, horseObserver()) }
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
        setUpBinding()
        setUpListeners()
        setUpRecyclerView()
        setUpPager()
        if (viewModel.horse.value?.typeOfSale == HorseAdsTypeEnum.AUCTION.value)
            viewModel.startHandleAuctionFinish()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
        binding?.layoutAuctionTimer?.viewModel = viewModel
    }

    private fun setUpRecyclerView() {
        dataViewAdapter = DataViewRecyclerAdapter(requireContext())
        binding?.rvDataView?.adapter = dataViewAdapter
        viewModel.horse.value?.let {
            dataViewAdapter.submitItems(
                arrayListOf(
                    DataView(resources.getString(R.string.horse_name), it.name),
                    DataView(resources.getString(R.string.horse_gender), it.sex),
                    DataView(resources.getString(R.string.horse_type), it.category?.name),
                    DataView(resources.getString(R.string.father_name), it.fatherName),
                    DataView(resources.getString(R.string.mother_name), it.motherName),
                    DataView(
                        resources.getString(R.string.age),
                        "${it.age} ${resources.getString(R.string.years_)}"
                    ),
                    DataView(resources.getString(R.string.height), it.height),
                    DataView(resources.getString(R.string.safety), it.safety),
                    DataView(
                        resources.getString(R.string.vaccinate),
                        if (it.isVaccinated == true) resources.getString(R.string.vaccinated) else "--"
                    )
                )
            )
        }
    }

    private fun setUpListeners() {
        binding?.btnWhatsapp?.setOnClickListener {
            viewModel.horse.value?.contactNumber.openWhatsApp(requireActivity())
        }
        binding?.btnCallUs?.setOnClickListener {
            requireActivity().openDial(viewModel.horse.value?.contactNumber)
        }
        binding?.btnJoinAuction?.setOnClickListener {
            navigationController.navigate(R.id.action_horseDetailsFragment_to_horseAuctionFragment)
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

    private fun horseObserver(): CustomObserverResponse<Horse> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<Horse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Horse?
                ) {
                    viewModel.horse.value = data
                    binding?.layoutToolbar?.isFavorite = data?.is_wishlist
                    init()
                }
            }, true
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

}