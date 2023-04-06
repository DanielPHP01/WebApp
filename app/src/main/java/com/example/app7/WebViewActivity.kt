package com.example.app7

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    lateinit var webView: WebView
    private var canBack = true
    private var link = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        supportActionBar?.hide()
        webView = findViewById(R.id.web_view_link)
        webViewNavigation()
        setWebView(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun setWebView(savedInstanceState: Bundle?) {
        val webViewSettings = webView.settings
        webViewSettings.javaScriptEnabled = true

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            getLinkAndLoad()
        }
        webViewSettings.domStorageEnabled = true
        webViewSettings.javaScriptCanOpenWindowsAutomatically = true
        setCookieManager(webViewSettings)
    }

    private fun setCookieManager(webViewSettings: WebSettings) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        webViewSettings.loadWithOverviewMode = true
        webViewSettings.useWideViewPort = true
        webViewSettings.domStorageEnabled = true
        webViewSettings.databaseEnabled = true
        webViewSettings.setSupportZoom(false)
        webViewSettings.allowFileAccess = true
        webViewSettings.allowContentAccess = true
        webViewSettings.loadWithOverviewMode = true
        webViewSettings.useWideViewPort = true
    }

    private fun getLinkAndLoad() {
        if (intent.getStringExtra(getString(R.string.link_key))?.isNotEmpty() == true) {
            webView.loadUrl(intent.getStringExtra(getString(R.string.link_key))!!)
            link = intent.getStringExtra(getString(R.string.link_key))!!
        }
    }

    private fun webViewNavigation() {
        webView.webViewClient = (object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("url", "$url")
                canBack = false
                canBack = !url.contentEquals(link)
            }
        })
    }

    override fun onBackPressed() {
        if (canBack) {
            webView.goBack()
        }
    }
}