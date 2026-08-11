package com.example.composetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.composetemplate.core.designsystem.theme.ComposeTemplateTheme

class MainActivity : ComponentActivity() {
    private val appContainer by lazy {
        (application as ComposeTemplateApplication).appContainer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTemplateTheme { ComposeTemplateApp(container = appContainer) }
        }
    }
}

