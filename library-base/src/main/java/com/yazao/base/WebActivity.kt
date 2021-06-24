package com.yazao.base

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.yazao.base.databinding.ActivityWebBinding
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.layout_toolbar.*

open class WebActivity : BaseToolbarActivityKt<ActivityWebBinding>() {

    companion object {
        open val WEB_TITLE = "title"
        open val WEB_URL = "resultUrl"
    }

    override fun getLayoutID(): Int = R.layout.activity_web

    override fun initData() {
        var title = intent.getStringExtra(WEB_TITLE)
        title?.let { setToolbar(toolbar, it) }
        val url = intent.getStringExtra(WEB_URL) as String
        webView.loadUrl(url)

        webViewSetting()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetting() {
        val settings: WebSettings = webView.settings
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        if (webView.isHardwareAccelerated) {
            settings.javaScriptEnabled = true
        }
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webView.webViewClient = WebViewClient()
    }

}