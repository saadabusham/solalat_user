package com.raantech.solalat.user.ui.payment.viewmodels

import android.content.Context
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val sharedPreferences: SharedPreferencesUtil
) : BaseViewModel() {

}