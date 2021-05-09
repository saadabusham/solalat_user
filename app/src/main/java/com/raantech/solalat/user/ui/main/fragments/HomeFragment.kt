package com.raantech.solalat.user.ui.main.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.GeneralError
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.HorseAdsTypeEnum
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.databinding.FragmentHomeBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.main.adapters.HorsesGridRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.disableViews
import com.raantech.solalat.user.utils.extensions.enableViews
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_home

    private val viewModel: MainViewModel by viewModels()
    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    lateinit var horsesGridRecyclerAdapter: HorsesGridRecyclerAdapter

    var refreshData: Boolean = false

    override fun onResume() {
        super.onResume()
        if (!refreshData) {
            refreshData = true
            return
        }
        horsesGridRecyclerAdapter.clear()
        loadData()
    }

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        loadingObserver()
        setUpListeners()
        init()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.tvAll?.setOnClickListener {
            viewModel.horseAdsTypeEnum.value = HorseAdsTypeEnum.ALL
            reloadData()
        }
        binding?.tvMazad?.setOnClickListener {
            viewModel.horseAdsTypeEnum.value = HorseAdsTypeEnum.AUCTION
            reloadData()
        }
        binding?.tvSell?.setOnClickListener {
            viewModel.horseAdsTypeEnum.value = HorseAdsTypeEnum.SELL
            reloadData()
        }
    }

    private fun init() {
        setUpRecyclerView()
        loadData()
    }

    private fun loadingObserver() {
        loading.observe(this, Observer {
            if (it) {
                binding?.root?.disableViews()
            } else {
                binding?.root?.enableViews()
            }
        })
    }

    private fun reloadData() {
        horsesGridRecyclerAdapter.clear()
        viewModel.getHorses(horsesGridRecyclerAdapter.itemCount).observe(this, horsesObserver())
    }

    private fun loadData() {
        viewModel.getHorses(horsesGridRecyclerAdapter.itemCount).observe(this, horsesObserver())
    }

    private fun setUpRecyclerView() {
        horsesGridRecyclerAdapter = HorsesGridRecyclerAdapter(requireContext())
        binding?.recyclerView?.adapter = horsesGridRecyclerAdapter
        binding?.recyclerView.setOnItemClickListener(this)
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && horsesGridRecyclerAdapter.itemCount > 0 && !isFinished) {
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

    private fun hideShowNoData() {
        if (horsesGridRecyclerAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.linearNoData?.visible()
        } else {
            binding?.layoutNoData?.linearNoData?.gone()
            binding?.recyclerView?.visible()
        }
    }


    private fun horsesObserver(): CustomObserverResponse<List<Horse>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<Horse>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<Horse>>?
                ) {
                    isFinished = data?.body?.isNullOrEmpty() == true
                    data?.body?.let {
                        horsesGridRecyclerAdapter.addItems(it)
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

    }

}