package com.example.ev2_master_martini.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ev2_master_martini.navigation.AppRoute

@Composable
fun AppBottomBar(
    selectedRoute: String,
    onHomeClick: () -> Unit,
    onCatalogClick: () -> Unit,
    onEventsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedRoute == AppRoute.Home.route,
            onClick = onHomeClick,
            icon = { Text("⌂") },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = selectedRoute == AppRoute.Catalog.route,
            onClick = onCatalogClick,
            icon = { Text("▦") },
            label = { Text("Catálogo") }
        )

        NavigationBarItem(
            selected = selectedRoute == AppRoute.Events.route,
            onClick = onEventsClick,
            icon = { Text("★") },
            label = { Text("Eventos") }
        )

        NavigationBarItem(
            selected = selectedRoute == AppRoute.Profile.route,
            onClick = onProfileClick,
            icon = { Text("●") },
            label = { Text("Perfil") }
        )
    }
}
