package com.raantech.solalat.user.ui.purchase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.enums.PurchaseStatusEnum
import com.raantech.solalat.user.data.models.Purchase
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.databinding.ActivityPurchasesBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.purchase.adapters.PurchasesRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class PurchasesActivity : BaseBindingActivity<ActivityPurchasesBinding>() {

    private val viewModel: PurchasesViewModel by viewModels()
    lateinit var adapter: PurchasesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_purchases,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.menu_my_purchases)
        )
        init()
    }

    private fun init() {
        setUpListeners()
        setUpAdapter()
//        viewModel.getFAQs().observe(this, faqsObserver())
    }

    private fun faqsObserver(): CustomObserverResponse<List<Purchase>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<Purchase>> {

                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Purchase>?
                ) {
                    data?.let { adapter.submitItems(it) }
                }
            }, showError = false
        )
    }


    private fun setUpAdapter() {
        adapter = PurchasesRecyclerAdapter(this)
        binding?.recyclerView?.adapter = adapter
        adapter.submitItems(
            arrayListOf(
                Purchase(
                    status = PurchaseStatusEnum.IN_THE_WAY.value,
                    items = arrayListOf(
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory()
                    )
                ),
                Purchase(
                    status = PurchaseStatusEnum.DELIVERED.value,
                    items = arrayListOf(
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory()
                    )
                ),
                Purchase(
                    status = PurchaseStatusEnum.DELIVERED.value,
                    items = arrayListOf(
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory()
                    )
                ),
                Purchase(
                    status = PurchaseStatusEnum.DELIVERED.value,
                    items = arrayListOf(
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory()
                    )
                ),
                Purchase(
                    status = PurchaseStatusEnum.IN_THE_WAY.value,
                    items = arrayListOf(
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory()
                    )
                ),
                Purchase(
                    status = PurchaseStatusEnum.IN_THE_WAY.value,
                    items = arrayListOf(
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory(),
                        Accessory()
                    )
                )
            )
        )
    }

    private fun setUpListeners() {

    }

    companion object {

        fun start(context: Context?) {
            val intent = Intent(context, PurchasesActivity::class.java)
            context?.startActivity(intent)
        }

    }

}