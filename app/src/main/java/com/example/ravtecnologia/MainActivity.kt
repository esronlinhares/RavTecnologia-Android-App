package com.example.ravtecnologia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ravtecnologia.ui.MainScreen
import com.example.ravtecnologia.ui.theme.RavTecnologiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RavTecnologiaTheme {
                MainScreen()
            }
        }
    }
}
