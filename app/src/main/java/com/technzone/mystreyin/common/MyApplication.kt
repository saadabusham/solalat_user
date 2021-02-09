package com.technzone.mystreyin.common

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@HiltAndroidApp
class MyApplication @Inject constructor() : Application() {

    public var deeplink_specialist_id = ""
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Branch.enableLogging();
        Branch.getAutoInstance(this);
    }
}
