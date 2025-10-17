package com.example.ravtecnologia // Pacote principal do app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ravtecnologia.ui.MainScreen
import com.example.ravtecnologia.ui.theme.RavTecnologiaTheme
// Imports necessários para usar Compose e o tema do app

class MainActivity : ComponentActivity() { // Activity principal do app
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Chama a implementação padrão do ciclo de vida

        enableEdgeToEdge() // Faz o app ocupar toda a tela (modo imersivo)

        setContent { // Define o conteúdo da tela usando Jetpack Compose
            RavTecnologiaTheme { // Aplica o tema visual do app
                MainScreen() // Exibe a tela principal
            }
        }
    }
}
