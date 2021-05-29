package com.raantech.solalat.user.ui.technicalsupport

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.databinding.ActivityTechnicalSupportBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.utils.extensions.showValidationErrorAlert
import com.raantech.solalat.user.utils.extensions.validate
import com.raantech.solalat.user.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class TechnicalSupportActivity : BaseBindingActivity<ActivityTechnicalSupportBinding>() {

    private val viewModel: TechnicalSupportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_technical_support,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true
        )
        binding?.viewModel = viewModel
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding?.btnSend?.setOnClickListener {
            if(isDataValid()){

            }
        }
    }

    private fun sendProblemResultObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {

                }
            })
    }


    private fun isDataValid(): Boolean {
        binding?.edTitle?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            this
        )
            .let {
                if (!it.isValid) {
                    showValidationErrorAlert(
                        title = resources.getString(R.string.horse_name),
                        message = it.errorMessage
                    )
                    return false
                }
            }
        binding?.edSummery?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            this
        )
            .let {
                if (!it.isValid) {
                    showValidationErrorAlert(
                        title = resources.getString(R.string.father_name),
                        message = it.errorMessage
                    )
                    return false
                }
            }
        return true
    }

    companion object {

        fun start(context: Context?) {
            val intent = Intent(context, TechnicalSupportActivity::class.java)
            context?.startActivity(intent)
        }

    }

}