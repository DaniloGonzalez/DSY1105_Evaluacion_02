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
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.RowScope
import com.example.ev2_master_martini.R
import com.example.ev2_master_martini.navigation.AppRoute

/**
 * CAMBIO REALIZADO (centralización de textos):
 * Las etiquetas de la barra inferior se toman de strings.xml en lugar de estar
 * hardcodeadas en el componente.
 */
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
            label = stringResource(R.string.nav_home),
            icon = Icons.Filled.Home,
            contentDescription = stringResource(R.string.nav_home),
            selected = selectedRoute == AppRoute.Home.route,
            onClick = onHomeClick
        )

        NavItem(
            label = stringResource(R.string.nav_catalog),
            icon = Icons.AutoMirrored.Filled.MenuBook,
            contentDescription = stringResource(R.string.nav_catalog),
            selected = selectedRoute == AppRoute.Catalog.route,
            onClick = onCatalogClick
        )

        NavItem(
            label = stringResource(R.string.nav_events),
            icon = Icons.Filled.Event,
            contentDescription = stringResource(R.string.nav_events),
            selected = selectedRoute == AppRoute.Events.route,
            onClick = onEventsClick
        )

        NavItem(
            label = stringResource(R.string.nav_profile),
            icon = Icons.Filled.Person,
            contentDescription = stringResource(R.string.nav_profile),
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
