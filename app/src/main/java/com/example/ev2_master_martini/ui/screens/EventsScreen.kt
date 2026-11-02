package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun EventsScreen(
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onCatalogClick: () -> Unit,
    onEventsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onEventClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Eventos y novedades",
                onBack = onBack
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedRoute = AppRoute.Events.route,
                onHomeClick = onHomeClick,
                onCatalogClick = onCatalogClick,
                onEventsClick = {},
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Eventos y novedades",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("Revisa las próximas actividades de Master Martini.")

            EventCard(
                title = "Taller de chocolatería",
                description = "Conoce nuevas técnicas para trabajar con chocolate.",
                availability = "Cupos disponibles",
                onClick = { onEventClick("taller_chocolateria") }
            )

            EventCard(
                title = "Novedades de productos",
                description = "Descubre nuevos insumos para tus preparaciones.",
                availability = "Ver novedades",
                onClick = { onEventClick("novedades_productos") }
            )

            EventCard(
                title = "Feria de pastelería",
                description = "Encuentro con proveedores y especialistas del rubro.",
                availability = "Inscripción abierta",
                onClick = { onEventClick("feria_pasteleria") }
            )

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}

@Composable
private fun EventCard(
    title: String,
    description: String,
    availability: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = com.example.ev2_master_martini.R.drawable.miniatura_evento
                ),
                contentDescription = "Miniatura del evento $title",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 120.dp, height = 72.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = availability,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Ver detalle",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsScreenPreview() {
    Ev2_Master_MartiniTheme {
        EventsScreen(
            onBack = {},
            onHomeClick = {},
            onCatalogClick = {},
            onEventsClick = {},
            onProfileClick = {},
            onEventClick = {}
        )
    }
}