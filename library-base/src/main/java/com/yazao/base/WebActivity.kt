package com.yazao.base

import android.annotation.SuppressLint
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.yazao.base.databinding.ActivityWebBinding

open class WebActivity : BaseToolbarActivityKt<ActivityWebBinding>() {

    companion object {
        open val WEB_TITLE = "title"
        open val WEB_URL = "resultUrl"
    }

    override fun getLayoutID(): Int = R.layout.activity_web

    override fun initData() {
        webViewSetting()

        var title = intent.getStringExtra(WEB_TITLE)
        title?.let { setToolbar(mDataBinding.toolbar, it) }
        val url = intent.getStringExtra(WEB_URL) as String
        mDataBinding.webView.loadUrl(url)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetting() {
        val settings: WebSettings = mDataBinding.webView.settings
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        if (mDataBinding.webView.isHardwareAccelerated) {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
        }
        settings.allowFileAccess = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.useWideViewPort = true
        settings.setSupportMultipleWindows(false)
        //settings.setAppCacheEnabled(true)
        // settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true)
        //settings.setAppCacheMaxSize(Long.MAX_VALUE)
        // settings.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        settings.pluginState = WebSettings.PluginState.ON_DEMAND
        // settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE;
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        mDataBinding.webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
//                super.onReceivedSslError(view, handler, error)
                handler?.proceed()//默认接受证书
            }
        }
    }

}