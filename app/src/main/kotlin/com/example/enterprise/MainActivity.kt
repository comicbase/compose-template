package com.example.enterprise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.enterprise.core.designsystem.theme.EnterpriseTheme
import com.example.enterprise.di.AppContainer

class MainActivity : ComponentActivity() {
    private val appContainer by lazy { AppContainer() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnterpriseTheme { EnterpriseApp(container = appContainer) }
        }
    }
}

