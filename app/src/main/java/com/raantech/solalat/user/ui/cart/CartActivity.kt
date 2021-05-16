package com.raantech.solalat.user.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.models.Price
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.databinding.ActivityCartBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.cart.adapters.CartRecyclerAdapter
import com.raantech.solalat.user.ui.cart.viewmodels.CartViewModel
import com.raantech.solalat.user.utils.extensions.gone
import com.raantech.solalat.user.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_favorite_toolbar.*

@AndroidEntryPoint
class CartActivity : BaseBindingActivity<ActivityCartBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: CartViewModel by viewModels()
    lateinit var cartRecyclerAdapter: CartRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_cart,
            hasToolbar = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.cart,
            hasBackButton = true,
            showBackArrow = true
        )
        setUpBinding()
        setUpListeners()
        setUpRecyclerView()
    }

    private fun setUpListeners() {
        binding?.btnPay?.setOnClickListener {
            PayedSuccessActivity.start(this)
        }
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpRecyclerView() {
        cartRecyclerAdapter = CartRecyclerAdapter(this)
        binding?.recyclerView?.adapter = cartRecyclerAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
        loadData()
    }

    private fun loadData() {
        viewModel.getCarts(
        ).observe(this, Observer {
            cartRecyclerAdapter.submitItems(it)
            calculattion()
            hideShowNoData()
        })
    }

    private fun calculattion() {
        binding?.count = cartRecyclerAdapter.itemCount
        var subtotal = 0.0
        cartRecyclerAdapter.items.forEach {
            subtotal += it.price?.amount?.toDouble()?.times(it.count ?:1)?:0.0
        }
        viewModel.subTotal.postValue(Price(amount = subtotal.toString(),formatted = "$subtotal ${resources.getString(R.string.sar)}"))
        viewModel.TAX_CONST?.times(subtotal)?.let {
            viewModel.tax.postValue(Price(amount = it.toString(),formatted = "${it} ${resources.getString(R.string.sar)}"))
            viewModel.total.postValue(Price(amount = (subtotal+it).toString(),formatted = "${(subtotal+it)} ${resources.getString(R.string.sar)}"))
        }
    }

    private fun hideShowNoData() {
        if (cartRecyclerAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.linearNoData?.visible()
            binding?.tvCount?.gone()
            binding?.btnPay?.gone()
            binding?.cvTotal?.gone()
            binding?.linearPrice?.gone()
        } else {
            binding?.layoutNoData?.linearNoData?.gone()
            binding?.recyclerView?.visible()
            binding?.tvCount?.visible()
            binding?.btnPay?.visible()
            binding?.cvTotal?.visible()
            binding?.linearPrice?.visible()
        }
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Accessory
        if (view?.id == R.id.imgPlus || view?.id == R.id.imgMinus) {
            viewModel.updateCartItem(item)
            calculattion()
        } else if (view?.id == R.id.cvDelete) {
            item.id?.let {
                viewModel.deleteCart(it)
                cartRecyclerAdapter.items.remove(item)
                cartRecyclerAdapter.notifyItemRemoved(position)
                calculattion()
                hideShowNoData()
            }
        }
    }

    companion object {
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, CartActivity::class.java)
            context?.startActivity(intent)
        }
    }

}