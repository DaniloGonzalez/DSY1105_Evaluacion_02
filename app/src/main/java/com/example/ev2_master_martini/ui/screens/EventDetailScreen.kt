package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.model.EventInfo
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

/**
 * CAMBIO REALIZADO:
 * 1. (separación de responsabilidades) el evento llega como modelo desde
 *    EventsViewModel; la pantalla no lo resuelve ni lo conoce.
 * 2. (Mejora 1) se elimina el botón "Volver a eventos" inferior: el retroceso
 *    queda solo en la barra superior.
 */
@Composable
fun EventDetailScreen(
    event: EventInfo,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detalle del evento",
                onBack = onBack
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
                text = event.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Fecha", style = MaterialTheme.typography.titleMedium)
                    Text(event.date)

                    Text("Lugar", style = MaterialTheme.typography.titleMedium)
                    Text(event.location)

                    Text("Descripción", style = MaterialTheme.typography.titleMedium)
                    Text(event.description)

                    Text("Estado", style = MaterialTheme.typography.titleMedium)
                    Text(event.availability)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        EventDetailScreen(
            event = EventInfo(
                id = "taller_chocolateria",
                title = "Taller de chocolatería",
                date = "15 de noviembre",
                location = "Santiago, Chile",
                description = "Participa en un taller práctico.",
                availability = "Cupos disponibles"
            ),
            onBack = {}
        )
    }
}
