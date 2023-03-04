package com.raantech.solalat.user.ui.payment.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.databinding.ActivityPaymentBinding
import com.raantech.solalat.user.ui.base.activity.BaseBindingActivity
import com.raantech.solalat.user.ui.payment.viewmodels.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity :
    BaseBindingActivity<ActivityPaymentBinding>() {
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_payment,
            hasToolbar = true,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = ""
        )
        setUpWebView()
    }

    private fun setUpWebView() {
        WebStorage.getInstance().deleteAllData()
        binding?.webView?.settings?.javaScriptEnabled = true
        binding?.webView?.settings?.javaScriptCanOpenWindowsAutomatically = true
        binding?.webView?.overScrollMode = WebView.OVER_SCROLL_NEVER

        binding?.webView?.settings?.allowFileAccessFromFileURLs = true
        binding?.webView?.settings?.allowUniversalAccessFromFileURLs = true
        binding?.webView?.settings?.loadWithOverviewMode = true
        binding?.webView?.settings?.useWideViewPort = true
        binding?.webView?.settings?.domStorageEnabled = true
        binding?.webView?.settings?.allowContentAccess = true
        binding?.webView?.settings?.mediaPlaybackRequiresUserGesture = false
        binding?.webView?.webViewClient = SSLTolerentWebViewClient()
        binding?.webView?.webChromeClient = ChromeWebViewClient()
        intent.getStringExtra(Constants.BundleData.PAYMENT_URL)?.let { loadUrl(it) }
    }

    private fun loadUrl(url: String) {
        binding?.webView?.loadUrl(url)
    }

    // SSL Error Tolerant Web View Client
    private inner class SSLTolerentWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            if (Uri.parse(url).getQueryParameter("status") == "paid") {
                setResult(RESULT_SUCCESS)
                finish()
            } else if (Uri.parse(url).getQueryParameter("status") == "failed") {
                setResult(RESULT_FAILED)
                finish()
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {

        }

    }

    // Chrome Web View Client
    private inner class ChromeWebViewClient : WebChromeClient() {

    }


    companion object {
        const val RESULT_SUCCESS = 10102
        const val RESULT_FAILED = 10103
        fun start(
            context: Context?,
            paymentUrl: String,
            resultLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra(Constants.BundleData.PAYMENT_URL, paymentUrl)
            }
            resultLauncher.launch(intent)
        }
    }

}