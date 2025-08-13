package com.webview.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.webview.practice.ui.theme.WebViewTheme
import com.webview.practice.ui.view.WebBrowser


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebViewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                     WebBrowser(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

