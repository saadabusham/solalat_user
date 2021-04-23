package com.saad.base.data.common

import android.app.Activity
import androidx.lifecycle.Observer
import com.saad.base.data.api.response.APIResource
import com.saad.base.data.api.response.RequestStatusEnum
import com.saad.base.data.api.response.ResponseSubErrorsCodeEnum
import com.saad.base.data.api.response.ResponseWrapper
import com.saad.base.ui.base.dialogs.CustomDialogUtils
import com.saad.base.utils.HandleRequestFailedUtil

class CustomObserverResponse<T>(
    private val activity: Activity,
    private val apiCallBack: APICallBack<T>,
    private val withProgress: Boolean = true
) : CustomDialogUtils(activity, withProgress, false),
    Observer<APIResource<ResponseWrapper<T>>> {

    override fun onChanged(responseWrapperResponse: APIResource<ResponseWrapper<T>>) {
        when (responseWrapperResponse?.status) {
            RequestStatusEnum.SUCCESS -> {
                if (withProgress) {
                    hideProgress()
                }
                if (responseWrapperResponse.statusSubCode == ResponseSubErrorsCodeEnum.Success) {
                    apiCallBack.onSuccess(
                        responseWrapperResponse.statusCode
                            ?: -1,
                        responseWrapperResponse.statusSubCode,
                        responseWrapperResponse.data?.data
                    )
                    apiCallBack.onSuccess(
                        responseWrapperResponse.statusCode
                            ?: -1,
                        responseWrapperResponse.statusSubCode,
                        responseWrapperResponse.data
                    )
                    apiCallBack.onSuccess(
                        responseWrapperResponse.statusCode
                            ?: -1, responseWrapperResponse.statusSubCode, responseWrapperResponse
                    )
                } else {
                    handleRequestFailedMessages(
                        responseWrapperResponse.statusSubCode,
                        responseWrapperResponse.messages
                    )
                    responseWrapperResponse.messages?.let {
                        responseWrapperResponse.statusSubCode?.let { it1 ->
                            apiCallBack.onError(it1, it)
                        }
                    }
                }
            }
            RequestStatusEnum.FAILED -> {
                if (withProgress) {
                    hideProgress()
                }
                handleRequestFailedMessages(
                    responseWrapperResponse.statusSubCode,
                    responseWrapperResponse.messages
                )
                responseWrapperResponse.messages?.let {
                    responseWrapperResponse.statusSubCode?.let { it1 ->
                        apiCallBack.onError(it1, it)
                    }
                }
            }
            RequestStatusEnum.LOADING -> {
                if (withProgress) {
                    showProgress()
                }
                apiCallBack.onLoading()
            }
        }
    }

    interface APICallBack<T> {
        fun onSuccess(statusCode: Int, subErrorCode: ResponseSubErrorsCodeEnum, data: T?) {}
        fun onSuccess(
            statusCode: Int,
            subErrorCode: ResponseSubErrorsCodeEnum,
            data: ResponseWrapper<T>?
        ) {
        }

        fun onSuccess(
            statusCode: Int,
            subErrorCode: ResponseSubErrorsCodeEnum,
            data: APIResource<ResponseWrapper<T>>
        ) {
        }

        fun onError(subErrorCode: ResponseSubErrorsCodeEnum, message: String) {}
        fun onLoading() {}
    }

    fun handleRequestFailedMessages(
        subErrorCode: ResponseSubErrorsCodeEnum?,
        requestMessage: String?
    ) {
        activity?.let {
            HandleRequestFailedUtil.handleRequestFailedMessages(
                it,
                subErrorCode,
                requestMessage
            )
        }

    }

}