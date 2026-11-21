package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.ev2_master_martini.R
import com.example.ev2_master_martini.model.EventInfo
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

/**
 * CAMBIO REALIZADO:
 * 1. (separación de responsabilidades) los eventos llegan desde
 *    EventsViewModel; la pantalla no los conoce.
 * 2. (Mejora 1) se elimina el botón "Volver" inferior redundante: el retroceso
 *    queda solo en la barra superior.
 */
@Composable
fun EventsScreen(
    events: List<EventInfo>,
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
                onEventsClick = onEventsClick,
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

            events.forEach { event ->
                EventCard(
                    event = event,
                    onClick = { onEventClick(event.id) }
                )
            }
        }
    }
}

@Composable
private fun EventCard(
    event: EventInfo,
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
                painter = painterResource(id = R.drawable.miniatura_evento),
                contentDescription = "Miniatura del evento ${event.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 120.dp, height = 72.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = event.availability,
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
            events = listOf(
                EventInfo(
                    id = "taller_chocolateria",
                    title = "Taller de chocolatería",
                    date = "15 de noviembre",
                    location = "Santiago, Chile",
                    description = "Conoce nuevas técnicas para trabajar con chocolate.",
                    availability = "Cupos disponibles"
                )
            ),
            onBack = {},
            onHomeClick = {},
            onCatalogClick = {},
            onEventsClick = {},
            onProfileClick = {},
            onEventClick = {}
        )
    }
}
