package com.example.localhtml

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.localhtml.ui.theme.LocalHtmlTheme
import com.example.localhtml.ui.view.MyApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalHtmlTheme {
                MyApp()
            }
        }
    }
}

