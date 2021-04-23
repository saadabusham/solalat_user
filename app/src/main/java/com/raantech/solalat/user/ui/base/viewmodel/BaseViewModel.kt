package com.raantech.solalat.user.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel : ViewModel(){


    var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable?.dispose()
        compositeDisposable = null
    }

}