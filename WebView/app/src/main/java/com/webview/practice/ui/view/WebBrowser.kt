package com.webview.practice.ui.view

import android.annotation.SuppressLint
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
fun WebBrowser(modifier: Modifier = Modifier) {

    var currentUrl by remember { mutableStateOf("https://www.laliga.com/en-GB") }
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var searchText by remember { mutableStateOf(TextFieldValue(currentUrl)) }
    var loadingProgress by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Web Browser") },
                actions = {
                    IconButton(onClick = {
                        val inputUrl = searchText.text
                        currentUrl = if (!inputUrl.startsWith("http://") && !inputUrl.startsWith("https://")) {
                            "https://$inputUrl"
                        } else {
                            inputUrl
                        }
                        webViewInstance?.loadUrl(currentUrl)
                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }

                    IconButton(onClick = { webViewInstance?.goBack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Page")
                    }
                    IconButton(onClick = { webViewInstance?.goForward() }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Page")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(Modifier.padding(paddingValues)) {

                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                        .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                ) {
                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        decorationBox = { innerTextField ->
                            if (searchText.text.isEmpty()) {
                                Text(text = "Enter URL", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                }

                if (loadingProgress in 1..99) {
                    LinearProgressIndicator(
                        progress = loadingProgress / 100f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }

                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    factory = { context ->
                        WebView(context).apply {
                            webChromeClient = object : WebChromeClient() {
                                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                    loadingProgress = newProgress
                                }
                            }
                            webViewClient = WebViewClient()
                            settings.javaScriptEnabled = true
                            loadUrl(currentUrl)
                            webViewInstance = this
                        }
                    },
                    update = { it.loadUrl(currentUrl) }
                )
            }
        }
    )
}
