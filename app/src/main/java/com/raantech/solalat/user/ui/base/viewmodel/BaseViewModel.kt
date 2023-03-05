package com.raantech.solalat.user.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel : ViewModel(){


    var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable?.dispose()
        compositeDisposable = null
    }

    fun getFcmToken(callback: (String) -> Unit = {}) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(task.result)
            } else {
                callback("")
            }
        }
    }
}