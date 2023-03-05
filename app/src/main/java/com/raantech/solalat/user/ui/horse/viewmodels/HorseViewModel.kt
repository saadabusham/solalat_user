package com.raantech.solalat.user.ui.horse.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.enums.WishListType
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.models.horses.HorseExtraData
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.horse.HorseRepo
import com.raantech.solalat.user.data.repos.wishlist.WishListRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HorseViewModel @Inject constructor(
    private val horseRepo: HorseRepo,
    private val configurationRepo: ConfigurationRepo,
    private val wishListRepo: WishListRepo
) : BaseViewModel() {

    var horseId: Int? = null
    var horse: MutableLiveData<Horse> = MutableLiveData()
    var horseExtraData: MutableLiveData<HorseExtraData> = MutableLiveData()
    var latestPrice: MutableLiveData<Double> = MutableLiveData()
    var days: MutableLiveData<Int> = MutableLiveData(0)
    var hours: MutableLiveData<Int> = MutableLiveData(0)
    var minutes: MutableLiveData<Int> = MutableLiveData(0)

    private val auctionCountDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(
            horse.value?.millisUntilFinish() ?: 0,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                refreshFinishingTime(millisUntilFinished)
            }

            override fun onFinish() {

            }
        }
    }

    private fun refreshFinishingTime(millisUntilFinished: Long) {
        val s: Long = millisUntilFinished / 1000
        val m = s / 60
        val h = m / 60
        val d = h / 24
        days.postValue(d.toInt())
        hours.postValue((h % 24).toInt())
        minutes.postValue((m % 60).toInt())
//        seconds.postValue((s % 60).toInt())
//        val time = days.toString() + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60
    }

    fun startHandleAuctionFinish() {
        auctionCountDownTimer.cancel()
        auctionCountDownTimer.start()
    }

    fun getHorse(
        id: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            horseRepo.getHorse(id)
        emit(response)
    }

    fun addToWishList(
        productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            wishListRepo.addToWishList(WishListType.HORSE.value, productId)
        emit(response)
    }

    fun removeFromWishList(
        productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            wishListRepo.removeFromWishList(productId, WishListType.HORSE.value)
        emit(response)
    }

    fun addAuctionSubscription(
        horseId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            horseRepo.addAuctionSubscription(horseId, "moyasar")
        emit(response)
    }

    fun cancelAuctionSubscription(
        horseId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            horseRepo.cancelAuctionSubscription(horseId)
        emit(response)
    }

    fun increaseAuctionSubscription(
        horseId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            horseRepo.increaseAuctionSubscription(horseId)
        emit(response)
    }

}