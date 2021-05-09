package com.raantech.solalat.user.ui.auth.login.fragments

import androidx.navigation.navGraphViewModels
import com.google.gson.reflect.TypeToken
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.GeneralError
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.auth.login.TokenModel
import com.raantech.solalat.user.data.models.auth.login.UserDetailsResponseModel
import com.raantech.solalat.user.data.pref.user.UserPref
import com.raantech.solalat.user.databinding.FragmentVerificationLoginBinding
import com.raantech.solalat.user.ui.auth.login.viewmodels.LoginViewModel
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.main.MainActivity
import com.raantech.solalat.user.utils.extensions.showErrorAlert
import com.raantech.solalat.user.utils.extensions.validate
import com.raantech.solalat.user.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_verification_login.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class VerificationLoginFragment : BaseBindingFragment<FragmentVerificationLoginBinding>() {

    private val viewModel: LoginViewModel by navGraphViewModels(R.id.auth_nav_graph) { defaultViewModelProviderFactory }

    override fun getLayoutId(): Int = R.layout.fragment_verification_login
    @Inject
    lateinit var prefs: UserPref
    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.empty_string
        )
        setUpViewsListeners()
        setUpData()
    }

    private fun setUpData() {
        binding?.viewModel = viewModel
        viewModel.startHandleResendSignUpPinCodeTimer()
    }

    private fun verifyOtpResultObserver(): CustomObserverResponse<UserDetailsResponseModel> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<UserDetailsResponseModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: UserDetailsResponseModel?
                ) {
                    data?.let {
                        viewModel.storeUser(it)
                        MainActivity.start(requireContext())
                    }
                }
            })
    }


    private fun sendOtpResultObserver(): CustomObserverResponse<TokenModel> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<TokenModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: TokenModel?
                ) {
                    viewModel.userTokenMutableLiveData.postValue(data?.token)
                    viewModel.startHandleResendSignUpPinCodeTimer()
                }
            })
    }

    private fun setUpViewsListeners() {
        otp_view.setAnimationEnable(true)
        binding?.tvTimeToResend?.setOnClickListener {
            if (viewModel.signUpResendPinCodeEnabled.value == true) {
            viewModel.resendVerificationCode().observe(this, sendOtpResultObserver())
            }
        }
        binding?.btnVerify?.setOnClickListener {
            if (validateInput()) {
                viewModel.verifyCode().observe(this, verifyOtpResultObserver())
            }
        }
    }

    private fun validateInput(): Boolean {
        otp_view.text.toString().validate(ValidatorInputTypesEnums.OTP, requireContext()).let {
            if (!it.isValid) {
                activity.showErrorAlert(it.errorTitle, it.errorMessage)
                return false
            }
        }
        return true
    }

}