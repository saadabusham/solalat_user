package com.raantech.solalat.user.ui.main.barn.fragments

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
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
import com.raantech.solalat.user.data.models.barn.Barn
import com.raantech.solalat.user.data.models.map.Address
import com.raantech.solalat.user.databinding.FragmentBarnBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.main.adapters.barn.BarnGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.barn.activities.BarnDetailsActivity
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.ui.map.MapActivity
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.visible
import com.raantech.solalat.user.utils.getLocationName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BarnFragment : BaseBindingFragment<FragmentBarnBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()

    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    lateinit var barnGridRecyclerAdapter: BarnGridRecyclerAdapter

    var refreshData: Boolean = false

    override fun onResume() {
        super.onResume()
        if (!refreshData) {
            refreshData = true
            return
        }
        barnGridRecyclerAdapter.clear()
        loadBarns()
    }

    override fun getLayoutId(): Int = R.layout.fragment_barn

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
        binding?.linearAddress?.setOnClickListener {
            MapActivity.start(requireActivity(), resultLauncher)
        }
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.address =
                    data?.getSerializableExtra(Constants.BundleData.ADDRESS) as Address
                binding?.tvAddress?.text =
                    getLocationName(
                        viewModel.address?.lat,
                        viewModel.address?.lon
                    )
                barnGridRecyclerAdapter.items.clear()
                loadBarns()
            }
        }

    private fun loadBarns() {
        viewModel.getBarns(
            barnGridRecyclerAdapter.itemCount,
            viewModel.address?.lat, viewModel.address?.lon
        ).observe(this, barnsObserver())
    }

    private fun setUpRecyclerView() {
        barnGridRecyclerAdapter = BarnGridRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = barnGridRecyclerAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && barnGridRecyclerAdapter.itemCount > 0 && !isFinished) {
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
        if (barnGridRecyclerAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.linearNoData?.visible()
        } else {
            binding?.layoutNoData?.linearNoData?.gone()
            binding?.recyclerView?.visible()
        }
    }
    private fun barnsObserver(): CustomObserverResponse<List<Barn>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Barn>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<Barn>>?
                ) {
                    isFinished = data?.body?.isNullOrEmpty() == true
                    data?.body?.let {
                        barnGridRecyclerAdapter.addItems(it)
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
        item as Barn
        BarnDetailsActivity.start(requireContext(), item)
    }


}