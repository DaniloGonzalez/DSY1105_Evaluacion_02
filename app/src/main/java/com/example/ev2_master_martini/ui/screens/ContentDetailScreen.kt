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
fun ContentDetailScreen(
    onBack: () -> Unit,
    onContactSales: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Detalle del contenido",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Curso de chocolatería",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Aprende técnicas de chocolatería y conoce los productos recomendados para tus preparaciones."
                )

                Text(
                    text = "Contenido: video técnico y ficha de productos."
                )
            }
        }

        Button(
            onClick = onContactSales,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Solicitar asesoría")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver al catálogo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        ContentDetailScreen(
            onBack = {},
            onContactSales = {}
        )
    }
}
