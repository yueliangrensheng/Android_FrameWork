package com.yazao.base

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.yazao.base.databinding.ActivityWebBinding

open class WebActivity : BaseToolbarActivityKt<ActivityWebBinding>() {

    companion object {
        open val WEB_TITLE = "title"
        open val WEB_URL = "resultUrl"
    }

    override fun getLayoutID(): Int = R.layout.activity_web

    override fun initData() {
        var title = intent.getStringExtra(WEB_TITLE)
        title?.let { setToolbar(mDataBinding.toolbar, it) }
        val url = intent.getStringExtra(WEB_URL) as String
        mDataBinding.webView.loadUrl(url)

        webViewSetting()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetting() {
        val settings: WebSettings = mDataBinding.webView.settings
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        if (mDataBinding.webView.isHardwareAccelerated) {
            settings.javaScriptEnabled = true
        }
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        mDataBinding.webView.webViewClient = WebViewClient()
    }

}