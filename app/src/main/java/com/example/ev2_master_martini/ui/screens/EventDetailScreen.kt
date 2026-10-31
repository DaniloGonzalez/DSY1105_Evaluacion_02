package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun EventDetailScreen(
    eventId: String,
    onBack: () -> Unit
) {
    val event = when (eventId) {
        "novedades_productos" -> EventDetail(
            title = "Novedades de productos",
            date = "20 de noviembre",
            location = "Centro de demostraciones Master Martini",
            description = "Conoce los nuevos productos e insumos disponibles para tus preparaciones.",
            availability = "Actividad abierta"
        )
        "feria_pasteleria" -> EventDetail(
            title = "Feria de pastelería",
            date = "5 de diciembre",
            location = "Santiago, Chile",
            description = "Encuentro con proveedores y especialistas del rubro gastronómico.",
            availability = "Inscripción abierta"
        )
        else -> EventDetail(
            title = "Taller de chocolatería",
            date = "15 de noviembre",
            location = "Santiago, Chile",
            description = "Participa en un taller práctico para aprender nuevas técnicas de trabajo con chocolate.",
            availability = "Cupos disponibles"
        )
    }

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

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver a eventos")
            }
        }
    }
}

private data class EventDetail(
    val title: String,
    val date: String,
    val location: String,
    val description: String,
    val availability: String
)

@Preview(showBackground = true)
@Composable
fun EventDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        EventDetailScreen(
            eventId = "taller_chocolateria",
            onBack = {}
        )
    }
}
