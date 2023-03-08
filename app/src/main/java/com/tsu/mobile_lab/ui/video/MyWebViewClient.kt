package com.tsu.mobile_lab.ui.video

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

private const val url = "https://learnenglish.britishcouncil.org/general-english/video-zone"

class MyWebViewClient: WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return !request?.url.toString().startsWith(url)
    }
}