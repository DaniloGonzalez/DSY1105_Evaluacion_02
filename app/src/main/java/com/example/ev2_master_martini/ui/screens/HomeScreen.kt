package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun HomeScreen(
    onCatalogClick: () -> Unit,
    onEventsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppBottomBar(
                selectedRoute = AppRoute.Home.route,
                onHomeClick = {},
                onCatalogClick = onCatalogClick,
                onEventsClick = onEventsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Bienvenido a Master Martini",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Encuentra contenidos y oportunidades para tu negocio gastronómico."
            )

            Button(
                onClick = onCatalogClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver catálogo")
            }

            Button(
                onClick = onEventsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver eventos y novedades")
            }

            Button(
                onClick = onProfileClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mi perfil")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Ev2_Master_MartiniTheme {
        HomeScreen(
            onCatalogClick = {},
            onEventsClick = {},
            onProfileClick = {}
        )
    }
}
