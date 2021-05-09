package com.raantech.solalat.user.common

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@HiltAndroidApp
class MyApplication @Inject constructor() : Application() {

    public var deeplink_id = ""
    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Branch.enableLogging()
        Branch.getAutoInstance(this);
    }

    fun getInstance(): MyApplication {
        return instance
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}
