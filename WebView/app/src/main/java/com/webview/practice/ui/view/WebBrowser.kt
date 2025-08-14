package com.webview.practice.ui.view

import android.annotation.SuppressLint
import android.view.View
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebBrowser(modifier: Modifier) {
    var currentUrl by remember { mutableStateOf("https://www.laliga.com/en-GB") }
    var urlInput by remember { mutableStateOf(TextFieldValue(currentUrl)) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    var progress by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Web Browser") },
                actions = {
                    IconButton(onClick = {
                        val entered = urlInput.text
                        val fixedUrl = if (!entered.startsWith("http")) "https://$entered" else entered
                        currentUrl = fixedUrl
                        webView?.loadUrl(fixedUrl)
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { webView?.goBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    IconButton(onClick = { webView?.goForward() }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Forward")
                    }
                }
            )
        },
        content = { padding ->
            Column(Modifier.padding(padding)) {

                Box(
                    Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                ) {
                    BasicTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        singleLine = true,
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        decorationBox = { inner ->
                            if (urlInput.text.isEmpty()) Text("Enter URL", color = Color.Gray)
                            inner()
                        }
                    )
                }

                if (progress in 1..99) {
                    LinearProgressIndicator(
                        progress = progress / 100f,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        WebView(context).apply {
                            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true

                            webViewClient = object : WebViewClient() {
                                override fun onRenderProcessGone(
                                    view: WebView?,
                                    detail: RenderProcessGoneDetail?
                                ): Boolean {
                                    view?.destroy()
                                    return true
                                }
                            }

                            webChromeClient = object : WebChromeClient() {
                                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                    progress = newProgress
                                }
                            }

                            loadUrl(currentUrl)
                            webView = this
                        }
                    },
                    update = { view ->
                        if (view.url != currentUrl) view.loadUrl(currentUrl)
                    }
                )
            }
        }
    )
}
