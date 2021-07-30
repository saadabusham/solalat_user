package com.raantech.solalat.user.ui.main.accessories.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.accessories.Accessory
import com.raantech.solalat.user.databinding.DialogAccessoryBinding
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

class AccessoriesDialog(
    val context: Activity,
    val lifecycle: LifecycleOwner,
    val accessory: Accessory,
    private val viewModel: MainViewModel,
    private val callBack: CallBack,
    private val position: Int
) :
    Dialog(context) {

    private lateinit var binding: DialogAccessoryBinding
    val count: MutableLiveData<Int> = MutableLiveData(1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent);
        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_accessory,
                null,
                false
            )
        binding.viewModel = this
        binding.isFavorite = accessory.isWishlist
        binding.lifecycleOwner = lifecycle
        setContentView(binding.root)
        setCancelable(true)
        checkCart()
    }

    fun onCancelClicked() {
        cancel()
        position.let { callBack.callBack(it,accessory) }
    }

    fun onAddToCartClicked() {
        accessory.count = count.value
        viewModel.addToCart(accessory)
        cancel()
    }

    fun checkCart() {
        accessory.id?.let {
            viewModel.getCart(it).observe(lifecycle, Observer {
                it?.count?.let {
                    count.postValue(it)
                }
            })
        }
    }

    fun  onFavoriteClicked(){
        if(accessory.isWishlist == true){
            binding.isFavorite = false
            accessory.id?.let { viewModel.removeFromWishList(it).observe(lifecycle,wishListActionObserver()) }
        }else{
            binding.isFavorite = true
            accessory.id?.let { viewModel.addToWishList(it).observe(lifecycle,wishListActionObserver()) }
        }
    }

    fun plus() {
        count.postValue(count.value?.plus(1))
    }

    fun minus() {
        if (count.value == 1)
            return
        count.postValue(count.value?.minus(1))
    }

    private fun wishListActionObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            context,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<Any>?
                ) {
                    accessory.isWishlist = accessory.isWishlist != true
                    binding.isFavorite = accessory.isWishlist
                }
            }, false
        )
    }

    interface CallBack{
        fun callBack(position:Int,accessory: Accessory)
    }

}
