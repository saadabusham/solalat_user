package com.technzone.mystreyin.utils.extensions

import android.app.Activity
import com.tapadoo.alerter.Alerter
import com.technzone.mystreyin.R
import com.technzone.mystreyin.data.models.configuration.UpdateStatus

fun Activity?.showErrorAlert(title: String = "", message: String) {
    Alerter.create(this)
        .setTitle(title)
        .setTitleAppearance(R.style.AlertTextAppearance)
        .setText(message)
        .setTextAppearance(R.style.AlertTextAppearance)
        .setBackgroundColorRes(R.color.alert_red) // or setBackgroundColorInt(Color.CYAN)
        .show()
}

//fun Activity.showUpdateDialog(updateStatus: UpdateStatus) {
//    UpdateAppDialog(this,updateStatus)
//        .show()
//}