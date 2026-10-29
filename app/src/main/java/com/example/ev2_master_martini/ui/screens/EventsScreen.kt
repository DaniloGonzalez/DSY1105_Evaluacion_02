package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun EventsScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Eventos y novedades",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Revisa las próximas actividades de Master Martini."
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Taller de chocolatería",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Conoce nuevas técnicas para trabajar con chocolate."
                )

                Text(
                    text = "Cupos disponibles"
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Novedades de productos",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Descubre nuevos insumos para tus preparaciones."
                )

                Text(
                    text = "Ver novedades"
                )
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver al inicio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventsScreenPreview() {
    Ev2_Master_MartiniTheme {
        EventsScreen(
            onBack = {}
        )
    }
}
