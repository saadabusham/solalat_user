package com.raantech.solalat.user.ui.auth.login.viewmodels

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.APIResource
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.data.enums.UserEnums
import com.raantech.solalat.user.data.models.auth.login.UserDetailsResponseModel
import com.raantech.solalat.user.data.repos.user.UserRepo
import com.raantech.solalat.user.ui.base.viewmodel.BaseViewModel
import com.raantech.solalat.user.utils.DateTimeUtil
import com.raantech.solalat.user.utils.extensions.*
import com.raantech.solalat.user.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    @ApplicationContext context: Context
) : BaseViewModel() {

    companion object {

        const val VALIDATION_CODE_LENGTH = 5

        const val RESEND_ENABLE_TIME_IN_MIN: Long = 1
        const val RESEND_ENABLE_TIME_UPDATE_TIMER_IN_SECOND: Long = 1
        const val JORDANIAN_PHONE_NUMBER_WITHOUT_COUNTRY_CODE_REGEX = "^(7|07)(7|8|9)([0-9]{7})\$"

    }

    val phoneNumberWithoutCountryCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val selectedCountryCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    // Login Verification Code
    val signUpVerificationCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val signUpResendPinCodeEnabled: MutableLiveData<Boolean>
            by lazy { MutableLiveData<Boolean>(false) }
    val signUpResendTimer: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val userTokenMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>("") }
    private val forgetCountDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(
            RESEND_ENABLE_TIME_IN_MIN.minToMillisecond(),
            RESEND_ENABLE_TIME_UPDATE_TIMER_IN_SECOND.secondToMillisecond()
        ) {
            override fun onTick(millisUntilFinished: Long) {
                signUpResendTimer.value =
                    millisUntilFinished.millisecondFormatting(DateTimeUtil.TIME_FORMATTING_MIN_AND_SECOND)
            }

            override fun onFinish() {
                signUpResendPinCodeEnabled.value = true
                signUpResendTimer.value = context.resources.getString(R.string.resend_code)
            }
        }
    }

    fun loginUser() = liveData {
        emit(APIResource.loading())
        val response = userRepo.login(
            phoneNumberWithoutCountryCode.value.toString().checkPhoneNumberFormat()
                .concatStrings(selectedCountryCode.value.toString())
        )
        emit(response)
    }


    fun storeUser(user: UserDetailsResponseModel) {
        signUpVerificationCode.postValue("")
        user.authToken?.let { userRepo.saveAccessToken(it) }
        userRepo.setUserStatus(UserEnums.UserState.LoggedIn)
        userRepo.setUser(user)
    }

    fun startHandleResendSignUpPinCodeTimer() {
        signUpResendPinCodeEnabled.value = false
        forgetCountDownTimer.cancel()
        forgetCountDownTimer.start()
    }

    fun verifyCode() = liveData {
        emit(APIResource.loading())
        val response = userRepo.verify(
            userTokenMutableLiveData.value.toString(),
            signUpVerificationCode.value.toString().toInt(),
            "",
            Constants.AppPlatform
        )
        emit(response)
    }

    fun resendVerificationCode() = liveData {
        emit(APIResource.loading())
        val response = userRepo.resendCode(
            userTokenMutableLiveData.value.toString()
        )
        emit(response)
    }
}