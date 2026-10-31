package com.example.ev2_master_martini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.navigation.AppNavHost
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // CAMBIO REALIZADO (accesibilidad "Modo texto grande"):
            // Se lee la preferencia del usuario desde DataStore. Si está
            // activa, el tema escala la tipografía (fontScale = 1.3f),
            // beneficiando a usuarios con dificultades visuales o baja
            // alfabetización digital (perfil del caso Master Martini).
            val context = LocalContext.current
            val repository = remember(context) {
                UserRepository(context.applicationContext)
            }
            val largeText by repository.isLargeText().collectAsState(initial = false)

            Ev2_Master_MartiniTheme(
                fontScale = if (largeText) 1.3f else 1f
            ) {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController
                )
            }
        }
    }
}