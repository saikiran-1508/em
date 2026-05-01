package com.example.embroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.embroid.ui.screens.MainApp
import com.example.embroid.ui.theme.EmbroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmbroidTheme {
                MainApp()
            }
        }
    }
}