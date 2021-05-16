package com.raantech.solalat.user.ui.main.accessories.activities

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
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.databinding.ActivityAccessoriesBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.main.accessories.dialogs.AccessoriesDialog
import com.raantech.solalat.user.ui.main.adapters.accessories.AccessoriesGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class AccessoriesActivity : BaseBindingActivity<ActivityAccessoriesBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false
    lateinit var medicalsRecyclerAdapter: AccessoriesGridRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.category =
            intent.getSerializableExtra(Constants.BundleData.CATEGORY) as ServiceCategory
        setContentView(
            layoutResID = R.layout.activity_accessories,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = viewModel.category?.name
        )
        setUpBinding()
        setUpRecyclerView()
        loadData()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRecyclerView() {
        medicalsRecyclerAdapter = AccessoriesGridRecyclerAdapter(this)
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


    private fun loadData() {
        viewModel.getAccessories(
            medicalsRecyclerAdapter.itemCount,
            viewModel.category?.id
        ).observe(this, accessoriesObserver())
    }


    private fun accessoriesObserver(): CustomObserverResponse<List<Accessory>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<Accessory>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<Accessory>>?
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
                        AccessoriesDialog(
                            this@AccessoriesActivity,
                            this@AccessoriesActivity,
                            it,
                            viewModel,
                            object : AccessoriesDialog.CallBack {
                                override fun callBack(position: Int, accessory: Accessory) {
                                    medicalsRecyclerAdapter.items[position].isWishlist =
                                        accessory.isWishlist
                                }

                            },
                            position
                        ).show()
                    }
                }
            }, true
        )
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Accessory
        if (item.isAvailable == true) {
            item.id?.let { viewModel.getAccessory(it).observe(this, accessoryObserver(position)) }
        }
    }


    companion object {
        fun start(
            context: Context?,
            category: ServiceCategory
        ) {
            val intent = Intent(context, AccessoriesActivity::class.java)
            intent.putExtra(Constants.BundleData.CATEGORY, category)
            context?.startActivity(intent)
        }

    }

}