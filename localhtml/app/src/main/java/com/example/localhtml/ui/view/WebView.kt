package com.example.localhtml.ui.view

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(name: String, age: String, address: String, email: String, phone: String, entryTime: String) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                val url = "file:///android_asset/index.html?name=$name&age=$age&address=$address&email=$email&phone=$phone&entryTime=$entryTime"
                loadUrl(url)
            }
        }
    )
}