package com.example.ev2_master_martini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.ev2_master_martini.navigation.AppNavHost
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import com.example.ev2_master_martini.viewmodel.SettingsViewModel
import com.example.ev2_master_martini.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // CAMBIO REALIZADO (separación de responsabilidades):
            // La Activity ya no lee DataStore directamente. La preferencia de
            // accesibilidad (texto grande) la expone SettingsViewModel, obtenido
            // a través del AppContainer (inyección de dependencias).
            val container = (application as MasterMartiniApp).container
            val factory = remember { ViewModelFactory(container) }
            val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
            val largeText by settingsViewModel.largeText.collectAsState(initial = false)

            Ev2_Master_MartiniTheme(
                fontScale = if (largeText) 1.3f else 1f
            ) {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    container = container
                )
            }
        }
    }
}
