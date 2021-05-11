package com.raantech.solalat.user.ui.horse.viewmodels

import android.os.CountDownTimer
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.raantech.solalat.user.data.models.horses.Horse
import com.raantech.solalat.user.data.repos.configuration.ConfigurationRepo
import com.raantech.solalat.user.data.repos.horse.HorseRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel

class HorseViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val horseRepo: HorseRepo,
        private val configurationRepo: ConfigurationRepo
) : BaseViewModel() {

    var horse: Horse? = null
    var days: MutableLiveData<Int> = MutableLiveData(0)
    var hours: MutableLiveData<Int> = MutableLiveData(0)
    var minutes: MutableLiveData<Int> = MutableLiveData(0)

    private val auctionCountDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(
                horse?.millisUntilFinish() ?: 0,
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

    }

    fun startHandleAuctionFinish() {
        auctionCountDownTimer.cancel()
        auctionCountDownTimer.start()
    }

}