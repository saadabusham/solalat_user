package com.raantech.solalat.user.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable?.plus(disposable: Disposable?): CompositeDisposable? {
    disposable?.let {
        this?.add(it)
    }
    return this
}