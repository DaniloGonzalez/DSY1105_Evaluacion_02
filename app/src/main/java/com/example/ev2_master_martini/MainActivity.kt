package com.example.ev2_master_martini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.ev2_master_martini.navigation.AppNavHost
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Ev2_Master_MartiniTheme {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController
                )
            }
        }
    }
}
