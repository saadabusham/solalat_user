package com.raantech.solalat.user.ui.wishlist.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.GeneralError
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.ServiceTypesEnum
import com.raantech.solalat.user.data.enums.WishListType
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.data.models.wishlist.WishList
import com.raantech.solalat.user.databinding.ActivityWishlistBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.horse.HorseActivity
import com.raantech.solalat.user.ui.main.accessories.dialogs.AccessoriesDialog
import com.raantech.solalat.user.ui.main.barn.activities.BarnDetailsActivity
import com.raantech.solalat.user.ui.main.truck.activities.TruckDetailsActivity
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.ui.wishlist.adapter.WishListRecyclerAdapter
import com.raantech.solalat.user.ui.wishlist.viewmodels.WishListViewModel
import com.raantech.solalat.user.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_favorite_toolbar.*

@AndroidEntryPoint
class WishListActivity : BaseBindingActivity<ActivityWishlistBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: WishListViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    var positionToUpdate: Int = -1
    lateinit var wishListRecyclerAdapter: WishListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_wishlist,
            hasToolbar = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.menu_favorites,
            hasBackButton = true,
            showBackArrow = true
        )
        setUpBinding()
        setUpListeners()
        setUpRecyclerView()
        loadData()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRecyclerView() {
        wishListRecyclerAdapter = WishListRecyclerAdapter(this)
        binding?.recyclerView?.adapter = wishListRecyclerAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && wishListRecyclerAdapter.itemCount > 0 && !isFinished) {
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
    }

    private fun loadData() {
        viewModel.getWishList(
            wishListRecyclerAdapter.itemCount
        ).observe(this, wishlistObserver())
    }


    private fun wishlistObserver(): CustomObserverResponse<List<WishList>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<WishList>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<WishList>>?
                ) {
                    isFinished = data?.body?.isNullOrEmpty() == true
                    data?.body?.let {
                        wishListRecyclerAdapter.addItems(it)
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
        if (wishListRecyclerAdapter.itemCount == 0) {
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
                    wishListRecyclerAdapter.items[positionToUpdate].isWishlist =
                        wishListRecyclerAdapter.items[positionToUpdate].isWishlist != true
                    wishListRecyclerAdapter.notifyItemChanged(positionToUpdate)
                    positionToUpdate = -1
                }
            }, true
        )
    }


    private fun accessoryObserver(position: Int): CustomObserverResponse<Accessory> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Accessory> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Accessory?
                ) {
                    data?.let {
                        if (it.isAvailable == true) {
                            AccessoriesDialog(
                                this@WishListActivity,
                                this@WishListActivity,
                                it,
                                mainViewModel,
                                object : AccessoriesDialog.CallBack {
                                    override fun callBack(position: Int, accessory: Accessory) {
                                        wishListRecyclerAdapter.items[position].isWishlist =
                                            accessory.isWishlist
                                        wishListRecyclerAdapter.notifyItemChanged(position)
                                    }

                                },
                                position
                            ).show()
                        } else {
                            showErrorAlert(
                                it.name ?: "",
                                String.format(
                                    resources.getString(R.string.available_at),
                                    it.dateOfAvailability.getDateWithMonthName()
                                )
                            )
                        }
                    }
                }
            }, true
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as WishList
        if (view?.id == R.id.imgFavorite) {
            positionToUpdate = position
            if (item.isWishlist == true) {
                viewModel.removeFromWishList(
                    if (item.type == ServiceTypesEnum.HORSES.value)
                        WishListType.HORSE.value else WishListType.PRODUCT.value, item.id ?: 0
                ).observe(this, wishListActionObserver())
            } else {
                viewModel.addToWishList(
                    if (item.type == ServiceTypesEnum.HORSES.value)
                        WishListType.HORSE.value else WishListType.PRODUCT.value,
                    item.id ?: 0
                ).observe(this, wishListActionObserver())
            }
        } else {
            when (item.type) {
                ServiceTypesEnum.BARN.value -> {
                    item.id?.let { BarnDetailsActivity.start(this, it) }
                }
                ServiceTypesEnum.ACCESSORIES.value -> {
                    item.id?.let {
                        viewModel.getAccessory(it).observe(this, accessoryObserver(position))
                    }
                }
                ServiceTypesEnum.TRANSPORTATION.value -> {
                    item.id?.let { TruckDetailsActivity.start(this, it) }
                }
                ServiceTypesEnum.HORSES.value -> {
                    item.id?.let { HorseActivity.start(this, it) }
                }
            }
        }
    }

    companion object {
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, WishListActivity::class.java)
            context?.startActivity(intent)
        }
    }

}