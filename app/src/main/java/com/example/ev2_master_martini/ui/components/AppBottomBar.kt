package com.example.ev2_master_martini.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.RowScope
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
        NavItem(
            label = "Inicio",
            icon = Icons.Filled.Home,
            contentDescription = "Ir a inicio",
            selected = selectedRoute == AppRoute.Home.route,
            onClick = onHomeClick
        )

        NavItem(
            label = "Contenidos",
            icon = Icons.AutoMirrored.Filled.MenuBook,
            contentDescription = "Ir al catálogo de contenidos",
            selected = selectedRoute == AppRoute.Catalog.route,
            onClick = onCatalogClick
        )

        NavItem(
            label = "Eventos",
            icon = Icons.Filled.Event,
            contentDescription = "Ir a eventos y novedades",
            selected = selectedRoute == AppRoute.Events.route,
            onClick = onEventsClick
        )

        NavItem(
            label = "Mi perfil",
            icon = Icons.Filled.Person,
            contentDescription = "Ir a mi perfil",
            selected = selectedRoute == AppRoute.Profile.route,
            onClick = onProfileClick
        )
    }
}

@Composable
private fun RowScope.NavItem(
    label: String,
    icon: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
        label = { Text(label) }
    )
}