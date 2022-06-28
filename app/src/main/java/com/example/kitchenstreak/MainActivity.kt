package com.example.kitchenstreak

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("SetJavaScriptEnabled")
class MainActivity : AppCompatActivity() {

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)

        webView?.webViewClient = WebViewClient()
        webView?.loadUrl("https://kitchenstreak.com/")

        val webSettings = webView?.settings
        webSettings?.javaScriptEnabled = true
    }

    override fun onBackPressed() {

        if (webView != null) {

            if (webView!!.canGoBack()) {
                webView!!.goBack()
            } else {
                super.onBackPressed()
            }

        } else {
            super.onBackPressed()
        }
    }
}