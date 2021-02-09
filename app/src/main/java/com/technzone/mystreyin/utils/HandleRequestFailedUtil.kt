package com.technzone.mystreyin.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.technzone.mystreyin.utils.extensions.shortToast
import com.technzone.mystreyin.R
import com.technzone.mystreyin.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.mystreyin.utils.extensions.showErrorAlert

object HandleRequestFailedUtil {

    fun handleRequestFailedMessages(
        context: Activity,
        fragmentManager: FragmentManager,
        apiErrorCode: Int?,
        serverErrorCode: ResponseSubErrorsCodeEnum?,
        requestMessage: String?
    ) {
        when (serverErrorCode) {
            ResponseSubErrorsCodeEnum.GENERAL_FAILED ->
                showDialogMessage(
                    requestMessage, context
                )
            ResponseSubErrorsCodeEnum.InvalidModel -> showDialogMessage(
                requestMessage, context
            )
            ResponseSubErrorsCodeEnum.Unauthorized -> showDialogMessage(
                requestMessage, context
            )
            ResponseSubErrorsCodeEnum.Forbidden -> showDialogMessage(
                requestMessage, context
            )
            ResponseSubErrorsCodeEnum.NotFound -> showDialogMessage(
                requestMessage, context
            )
            else -> showDialogMessage(
                requestMessage, context
            )
        }
    }

    fun showDialogMessage(
        requestMessage: String?,
        context: Activity
    ) {
        context.showErrorAlert(
            title = context.getString(R.string.alert_dialog_title),
            message = requestMessage!!
        )
    }

    private fun showToastMessage(message: String?, context: Context) {
        message?.let {
            context.shortToast(it)
        }
    }

}